package com.lixiaomi.baselibapplication.utils.aes_rsa_utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @describe：AES工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2019/4/8<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class AESUtil {
    private static final String AES = "AES";
    private static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param iv
     * @return
     */
    public static String encrypt(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), AES);
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64Utils.encodeBase64String(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), AES);
            IvParameterSpec ivspec = new IvParameterSpec(key.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64Utils.encodeBase64String(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] check(byte[] original) {
        int number = 0;
        for (int i = original.length - 1; i >= 0; i--) {
            if (original[i] == 0) {
                number++;
            } else {
                break;
            }
        }
        byte[] returnByte = new byte[original.length - number];
        for (int i = 0; i < returnByte.length; i++) {
            returnByte[i] = original[i];
        }
        return returnByte;
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) {
        try {
            byte[] encrypted1 = Base64Utils.decodeBase64(data);
            Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), AES);
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            byte[] check = check(original);
            String originalString = new String(check, "UTF-8");
            return originalString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data, String key) {
        try {
            byte[] encrypted1 = Base64Utils.decodeBase64(data);
            Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), AES);
            IvParameterSpec ivspec = new IvParameterSpec(key.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            byte[] check = check(original);
            String originalString = new String(check, "UTF-8");
            return originalString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        String key = "1234567812345678";
        String iv = "1234567812345678";
//        String msh = "{\"code\":0,\"msg\":\"操作成功\",\"serverTime\":1554775623350,\"data\":{\"Token\":\"f37c067fc7f644d78851a5ab008a016f\",\"ExpiresTime\":\"2019-04-10 10:07:03\",\"UserName\":\"系统管理员\"},\"extData\":null}";
//        String encrypt = encrypt(msh, key, iv);
//        System.out.println("encrypt:" + encrypt);
////        {"code":0,"msg":"操作成功","serverTime":1554775623350,"data":{"Token":"f37c067fc7f644d78851a5ab008a016f","ExpiresTime":"2019-04-10 10:07:03","UserName":"系统管理员"},"extData":null}
////        {"code":0,"msg":"操作成功","serverTime":1554775623350,"data":{"Token":"f37c067fc7f644d78851a5ab008a016f","ExpiresTime":"2019-04-10 10:07:03","UserName":"系统管理员"},"extData":null}
        String s = desEncrypt("Rtw2+5IbVL7MXaqqFQxP6Zk4GHHBt6bCHDHmSBnY4rDR3xM4+gMeWrSkznasajsaHhxvauaf9KSK94i/J14K8Ifq/5jQq2g9szGGOnv/cGwp8mAVNFK3WEa/ghBT7yyJrMbUOVdXT3LitxvJ44050hA3GJibyGPWLjr71Qr4vdd5JR43V/DtbecyLwJeqbn+1tG/bUt9NzZTcVpLsEexhFDKo19tAZYQwqYSdieY1pllX9yzDUBsJY/QRtba1y7o", key, iv);
//        String s = desEncrypt(encrypt, key, iv);
        System.out.println(s);
    }
}
