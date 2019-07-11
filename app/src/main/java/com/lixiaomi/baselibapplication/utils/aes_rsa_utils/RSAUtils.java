package com.lixiaomi.baselibapplication.utils.aes_rsa_utils;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @describe：RSA工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/8<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class RSAUtils {
//    /**
//     * 签名
//     *
//     * @param data       待签名数据
//     * @param privateKey 私钥
//     * @return 签名
//     */
//    public static String sign(String data, PrivateKey privateKey) throws Exception {
//        byte[] keyBytes = privateKey.getEncoded();
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PrivateKey key = keyFactory.generatePrivate(keySpec);
////        Signature signature = Signature.getInstance("MD5withRSA");
//        Signature signature = Signature.getInstance("SHA1withRSA");
//        signature.initSign(key);
//        signature.update(data.getBytes());
//        return new String(Base64Utils.encodeBase64(signature.sign()));
//    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64Utils.decodeBase64(sign.getBytes()));
    }


    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
//    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final int MAX_DECRYPT_BLOCK = 256;

    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64Utils.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 返回RSA公钥
     *
     * @param modulus
     * @param exponent
     * @return
     */
    public static PublicKey getPublicKey(String modulus, String exponent) {
        try {
//            byte[] m = decodeBase641(modulus);
//            byte[] e = decodeBase641(exponent);

            byte[] m = Base64Utils.decodeBase64(modulus);
            byte[] e = Base64Utils.decodeBase64(exponent);
            BigInteger b1 = new BigInteger(1, m);
            BigInteger b2 = new BigInteger(1, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64Utils.decodeBase64(publicKey.getBytes());

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        PublicKey publicKey1 = keyFactory.generatePublic(keySpec);

        return publicKey1;
    }

    public static PublicKey getPublicKey1(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64Utils.decodeBase64(publicKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {

        //Java
//        Cipher cipher = Cipher.getInstance("RSA");
        //android
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64Utils.encodeBase64String(encryptedData));
    }


    /**
     * RSA解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
//        java
//        Cipher cipher = Cipher.getInstance("RSA");
        //android
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64Utils.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }


    public static void main(String[] args) throws Exception {
        PublicKey aqab = RSAUtils.getPublicKey("siG96iYBD8R3+zyJxacBN8W7zsMZ4ShtyHQbsJJSTXhmUZRqd8V7JybvrSvOMni/44B0ljBFhKyZVWP6hfuGSh0bkCmrxXOwUh0hX7R076bpYv6mrKWjEdpISuC/HnTu+96arrHKhJvuBGmiPjSrW2ybp6heSxCcK37xh7PtTws=", "AQAB");
        String s = RSAUtils.encrypt("xiaomi", aqab);

//        PublicKey aqab = getPublicKey("siG96iYBD8R3+zyJxacBN8W7zsMZ4ShtyHQbsJJSTXhmUZRqd8V7JybvrSvOMni/44B0ljBFhKyZVWP6hfuGSh0bkCmrxXOwUh0hX7R076bpYv6mrKWjEdpISuC/HnTu+96arrHKhJvuBGmiPjSrW2ybp6heSxCcK37xh7PtTws=AQAB");
//        PrivateKey aqab1 = getPrivateKey("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALIhveomAQ/Ed/s8icWnATfFu87DGeEobch0G7CSUk14ZlGUanfFeycm760rzjJ4v+OAdJYwRYSsmVVj+oX7hkodG5Apq8VzsFIdIV+0dO+m6WL+pqyloxHaSErgvx507vvemq6xyoSb7gRpoj40q1tsm6eoXksQnCt+8Yez7U8LAgMBAAECgYEAqemXju57Wr/sNoCTZi6b8cgyEdJ2MBhxR6DA/NdFnFH5P13ThLWheEIiostDH0+gsfBIxS38UcaVaCc6w2ydtR1zDi/rY8Ic884pXKtLDgx44Y1DRzP9o50QUgXEGo6eeks+Y5iLViDGsWysxh8MW19+Z/0b22spSi+p9leFZXkCQQD7MMYJHGEpUfZlYKjSmbVd8i+0mDo4A0Sq2z3Av5VL0apadaT+pjN5TjOQgf7apNA9q44SpJUoWJgp1Epc8IIvAkEAtYrdxY85pUpN/pX8fjTGXqQ7iEAJjfGbmSvRvatH59JDTZYY7xpx40Xn8G4+lzenss+Nx0syXfByWbAZi9UV5QJBANYmed+Qm2E/6GH7q1KJCvACJxu/YHsahjNn4NjwPNc79BFVFQqpJV85BYsg2OcM9iBtnx1TxXp1NZ0LyS49Oa8CQG20wVgFuVw9juxuTuslkUrYDHHF4QA6JUFmzZTE0OsykSFttxum63PgZs1Qkdpn85uG+bKZqpJCH5g7ZvStjFECQQDsDjg+gvtgsaqB+HaYlxshlA4S8zxZUmBP+kkCRruk/rHgR6SyaXf0V++/w7gTbBEwfDKiGEAQioulxHHCCSk0");
//        String encrypt = encrypt("xiaomi", aqab);
        System.out.println(s);
//        String decrypt = decrypt(encrypt, aqab1);
//        System.out.println(decrypt);
    }

}
