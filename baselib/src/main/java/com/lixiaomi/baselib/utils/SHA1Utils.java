package com.lixiaomi.baselib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * @describe：获取当前apk的SHA1<br>
 * @author：Xiaomi<br>
 * @createTime：2016/10/11<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class SHA1Utils {

    /**
     * 获取当前apk的SHA1值
     *
     * @param context 上下文
     * @return 返回SHA1值
     * @throws PackageManager.NameNotFoundException
     * @throws NoSuchAlgorithmException
     */
    public static String getSHA1(Context context) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        PackageInfo info = context.getPackageManager().getPackageInfo(
                context.getPackageName(), PackageManager.GET_SIGNATURES);
        byte[] cert = info.signatures[0].toByteArray();
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] publicKey = md.digest(cert);
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < publicKey.length; i++) {
            String appendString = Integer.toHexString(0xFF & publicKey[i])
                    .toUpperCase(Locale.US);
            if (appendString.length() == 1) {
                hexString.append("0");
            }
            hexString.append(appendString);
            hexString.append(":");
        }
        String result = hexString.toString();
        return result.substring(0, result.length() - 1);
    }
}
