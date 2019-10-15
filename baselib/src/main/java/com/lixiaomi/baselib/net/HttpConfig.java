package com.lixiaomi.baselib.net;

import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Interceptor;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/10/14<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class HttpConfig {
    /**
     * 主机地址
     */
    private String HTTP_BASE_API = "";
    /**
     * okhttp的拦截器
     */
    private ArrayList<Interceptor> HTTP_INTERCEPTOR = new ArrayList<Interceptor>();
    /**
     * 网络拦截器
     */
    private ArrayList<Interceptor> HTTP_NETWORK_INTERCEPTOR = new ArrayList<Interceptor>();
    /**
     * 是否信任所有证书
     */
    private Boolean HTTP_CERTIFICATE_FLAG = true;
    /**
     * 证书流
     */
    private InputStream HTTP_CERTIFICATE_INPUT = null;
    /**
     * 链接失败后是否重试
     */
    private Boolean HTTP_RETRY_CONNECTION = true;
    /**
     * 默认token超时code
     */
    private int TOKEN_TIME_OUT_CODE = 401;
    /**
     * token过期提示
     */
    private String TOKEN_TIME_OUT_STR = "请求超时，请重新登陆";
    /**
     * 网络成功code码
     */
    private int HTTP_SUCCESS_CODE = 200;
    /**
     * 默认后端返回成功code
     */
    private int SERVER_SUCCESS_CODE = 0;
    /**
     * 包含该字符串默认后端返回数据异常
     */
    private String SERVER_ERROR_STR = "<html>";
    /**
     * 把不需要过滤的url 放到这个数组里边
     */
    private ArrayList<String> INTERCEPTOR_URL_LIST = new ArrayList<String>();


    public HttpConfig(String HTTP_BASE_API, ArrayList<Interceptor> HTTP_INTERCEPTOR, ArrayList<Interceptor> HTTP_NETWORK_INTERCEPTOR, Boolean HTTP_CERTIFICATE_FLAG,
                      InputStream HTTP_CERTIFICATE_INPUT, Boolean HTTP_RETRY_CONNECTION, int TOKEN_TIME_OUT_CODE, String TOKEN_TIME_OUT_STR,
                      int HTTP_SUCCESS_CODE, int SERVER_SUCCESS_CODE, String SERVER_ERROR_STR, ArrayList<String> INTERCEPTOR_URL_LIST) {
        this.HTTP_BASE_API = HTTP_BASE_API;
        this.HTTP_INTERCEPTOR = HTTP_INTERCEPTOR;
        this.HTTP_NETWORK_INTERCEPTOR = HTTP_NETWORK_INTERCEPTOR;
        this.HTTP_CERTIFICATE_FLAG = HTTP_CERTIFICATE_FLAG;
        this.HTTP_CERTIFICATE_INPUT = HTTP_CERTIFICATE_INPUT;
        this.HTTP_RETRY_CONNECTION = HTTP_RETRY_CONNECTION;
        this.TOKEN_TIME_OUT_CODE = TOKEN_TIME_OUT_CODE;
        this.TOKEN_TIME_OUT_STR = TOKEN_TIME_OUT_STR;
        this.HTTP_SUCCESS_CODE = HTTP_SUCCESS_CODE;
        this.SERVER_SUCCESS_CODE = SERVER_SUCCESS_CODE;
        this.SERVER_ERROR_STR = SERVER_ERROR_STR;
        this.INTERCEPTOR_URL_LIST = INTERCEPTOR_URL_LIST;
    }


    public String getTOKEN_TIME_OUT_STR() {
        return TOKEN_TIME_OUT_STR;
    }

    public void setTOKEN_TIME_OUT_STR(String TOKEN_TIME_OUT_STR) {
        this.TOKEN_TIME_OUT_STR = TOKEN_TIME_OUT_STR;
    }

    public String getHTTP_BASE_API() {
        return HTTP_BASE_API;
    }

    public void setHTTP_BASE_API(String HTTP_BASE_API) {
        this.HTTP_BASE_API = HTTP_BASE_API;
    }

    public ArrayList<Interceptor> getHTTP_INTERCEPTOR() {
        return HTTP_INTERCEPTOR;
    }

    public void setHTTP_INTERCEPTOR(ArrayList<Interceptor> HTTP_INTERCEPTOR) {
        this.HTTP_INTERCEPTOR.clear();
        this.HTTP_INTERCEPTOR.addAll(HTTP_INTERCEPTOR);
    }

    public ArrayList<Interceptor> getHTTP_NETWORK_INTERCEPTOR() {
        return HTTP_NETWORK_INTERCEPTOR;
    }

    public void setHTTP_NETWORK_INTERCEPTOR(ArrayList<Interceptor> HTTP_NETWORK_INTERCEPTOR) {
        this.HTTP_NETWORK_INTERCEPTOR.clear();
        this.HTTP_NETWORK_INTERCEPTOR.addAll(HTTP_NETWORK_INTERCEPTOR);
    }

    public Boolean getHTTP_CERTIFICATE_FLAG() {
        return HTTP_CERTIFICATE_FLAG;
    }

    public void setHTTP_CERTIFICATE_FLAG(Boolean HTTP_CERTIFICATE_FLAG) {
        this.HTTP_CERTIFICATE_FLAG = HTTP_CERTIFICATE_FLAG;
    }

    public InputStream getHTTP_CERTIFICATE_INPUT() {
        return HTTP_CERTIFICATE_INPUT;
    }

    public void setHTTP_CERTIFICATE_INPUT(InputStream HTTP_CERTIFICATE_INPUT) {
        this.HTTP_CERTIFICATE_INPUT = HTTP_CERTIFICATE_INPUT;
    }

    public Boolean getHTTP_RETRY_CONNECTION() {
        return HTTP_RETRY_CONNECTION;
    }

    public void setHTTP_RETRY_CONNECTION(Boolean HTTP_RETRY_CONNECTION) {
        this.HTTP_RETRY_CONNECTION = HTTP_RETRY_CONNECTION;
    }

    public int getTOKEN_TIME_OUT_CODE() {
        return TOKEN_TIME_OUT_CODE;
    }

    public void setTOKEN_TIME_OUT_CODE(int TOKEN_TIME_OUT_CODE) {
        this.TOKEN_TIME_OUT_CODE = TOKEN_TIME_OUT_CODE;
    }

    public int getHTTP_SUCCESS_CODE() {
        return HTTP_SUCCESS_CODE;
    }

    public void setHTTP_SUCCESS_CODE(int HTTP_SUCCESS_CODE) {
        this.HTTP_SUCCESS_CODE = HTTP_SUCCESS_CODE;
    }

    public int getSERVER_SUCCESS_CODE() {
        return SERVER_SUCCESS_CODE;
    }

    public void setSERVER_SUCCESS_CODE(int SERVER_SUCCESS_CODE) {
        this.SERVER_SUCCESS_CODE = SERVER_SUCCESS_CODE;
    }

    public String getSERVER_ERROR_STR() {
        return SERVER_ERROR_STR;
    }

    public void setSERVER_ERROR_STR(String SERVER_ERROR_STR) {
        this.SERVER_ERROR_STR = SERVER_ERROR_STR;
    }

    public ArrayList<String> getINTERCEPTOR_URL_LIST() {
        return INTERCEPTOR_URL_LIST;
    }

    public void setINTERCEPTOR_URL_LIST(ArrayList<String> INTERCEPTOR_URL_LIST) {
        this.INTERCEPTOR_URL_LIST.clear();
        this.INTERCEPTOR_URL_LIST.addAll(INTERCEPTOR_URL_LIST);
    }
}
