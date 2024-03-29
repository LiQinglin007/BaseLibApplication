package com.lixiaomi.baselib.net.okhttp;


import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;
import com.lixiaomi.baselib.net.HttpConfig;
import com.lixiaomi.baselib.utils.FileUtil;
import com.lixiaomi.baselib.utils.LogUtils;

import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @describe：获取okhttpClient<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/31<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public final class MiOkHttpClient {

    /**
     * 获取OkHttpClient
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        return OkHttpHolder.OK_HTTP_CLIENT;
    }

    private static final class OkHttpHolder {
        private static HttpConfig httpConfigData;

        static {
            httpConfigData = AppConfigInIt.getConfiguration(AppConfigType.HTTP_CONFIG);
        }

        private final static OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private final static long CONNECT_TIME_OUT = 20;
        private final static long WRITE_TIME_OUT = 20;
        private final static long READ_TIME_OUT = 20;

        /**
         * 给okhttp添加拦截器
         *
         * @return
         */
        private static OkHttpClient.Builder addInterceptors() {
            //应用拦截器
            ArrayList<Interceptor> http_interceptor = httpConfigData.getHTTP_INTERCEPTOR();
            ArrayList<Interceptor> http_network_interceptor = httpConfigData.getHTTP_NETWORK_INTERCEPTOR();
            if (http_interceptor != null && !http_interceptor.isEmpty()) {
                for (Interceptor interceptor : http_interceptor) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            //网络拦截器
            if (http_network_interceptor != null && !http_network_interceptor.isEmpty()) {
                for (Interceptor interceptor : http_network_interceptor) {
                    BUILDER.addNetworkInterceptor(interceptor);
                }
            }
            return BUILDER;
        }


        /**
         * 配置证书
         */
        private static final OkHttpClient.Builder addCertificate() {
            return addInterceptors()
                    //设置忽略证书 兼容https
                    .sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
        }

        //配置缓存的路径，和缓存空间的大小 10M
        static Cache cache = new Cache(new File(FileUtil.HTTP_CACHE_DIR), 10 * 10 * 1024);

        private static final OkHttpClient OK_HTTP_CLIENT =
                addCertificate()
                        //链接超时
                        .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                        //读取超时
                        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                        //写入超时
                        .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                        //链接失败后是否重试默认为true
                        .retryOnConnectionFailure(httpConfigData.getHTTP_RETRY_CONNECTION())
                        //设置使用缓存
                        .cache(cache)
                        .build();
    }

    /**
     * 配置忽略证书 https
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    /**
     * 配置忽略证书 https
     *
     * @return
     */
    public static class TrustAllCerts implements X509TrustManager {
        private static HttpConfig httpConfigData;

        static {
            httpConfigData = AppConfigInIt.getConfiguration(AppConfigType.HTTP_CONFIG);
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            boolean configurationFlag = httpConfigData.getHTTP_CERTIFICATE_FLAG();
            InputStream configurationInput = httpConfigData.getHTTP_CERTIFICATE_INPUT();
            if (configurationFlag || configurationInput == null) {
                //信任所有证书
                LogUtils.logd("信任所有证书");
                return;
            } else {
                LogUtils.logd("配置证书");
                if (chain == null) {
                    throw new CertificateException("checkServerTrusted: X509Certificate array is null");
                }
                if (chain.length < 1) {
                    throw new CertificateException("checkServerTrusted: X509Certificate is empty");
                }
                if (!(null != authType && authType.equals("ECDHE_RSA"))) {
                    throw new CertificateException("checkServerTrusted: AuthType is not ECDHE_RSA");
                }

                //检查所有证书
                try {
                    TrustManagerFactory factory = TrustManagerFactory.getInstance("X509");
                    factory.init((KeyStore) null);
                    for (TrustManager trustManager : factory.getTrustManagers()) {
                        ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }

                //获取本地证书中的信息
                String clientEncoded = "";
                String clientSubject = "";
                String clientIssUser = "";

                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(configurationInput);
                clientEncoded = new BigInteger(1, clientCertificate.getPublicKey().getEncoded()).toString(16);
                clientSubject = clientCertificate.getSubjectDN().getName();
                clientIssUser = clientCertificate.getIssuerDN().getName();

                //获取网络中的证书信息
                X509Certificate certificate = chain[0];
                PublicKey publicKey = certificate.getPublicKey();
                String serverEncoded = new BigInteger(1, publicKey.getEncoded()).toString(16);

                if (!clientEncoded.equals(serverEncoded)) {
                    throw new CertificateException("server's PublicKey is not equals to client's PublicKey");
                }
                String subject = certificate.getSubjectDN().getName();
                if (!clientSubject.equals(subject)) {
                    throw new CertificateException("server's subject is not equals to client's subject");
                }
                String issuser = certificate.getIssuerDN().getName();
                if (!clientIssUser.equals(issuser)) {
                    throw new CertificateException("server's issuser is not equals to client's issuser");
                }
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
