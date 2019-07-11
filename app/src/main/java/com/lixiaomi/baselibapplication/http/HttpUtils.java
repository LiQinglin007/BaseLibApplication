package com.lixiaomi.baselibapplication.http;



import com.lixiaomi.baselib.utils.MiRandomUtils;

import java.util.WeakHashMap;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/7/4<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class HttpUtils {
    public static WeakHashMap<String, String> getHeads(String aesPassword) {
//        try {
//            Context context = AppConfigInIt.getApplicationContext();
//            PublicKey aqab = RSAUtils.getPublicKey(context.getResources().getString(R.string.res_public_key), context.getResources().getString(R.string.res_public_key_type));
//            String s = RSAUtils.encrypt(aesPassword, aqab);
//            WeakHashMap<String, String> heads = new WeakHashMap<>();
//            heads.put(HttpData.TOKEN, PreferenceUtils.getString(FinalData.TOKEN, ""));
//            heads.put(HttpData.CLIENT_SN, PreferenceUtils.getString(FinalData.ANDROID_ID, ""));
//            heads.put(HttpData.DATA_ENCRYPT_KEY, s);
//            heads.put(HttpData.DATA_ENCRYPT_KEY_ID, context.getResources().getString(R.string.res_public_key_id));
//            return heads;
//        } catch (Exception e) {
//            LogUtils.loge(TAG, "请求头加密出错：" + e.toString());
//            return null;
//        }
        return null;
    }

    public static String getAESPassword() {
        return MiRandomUtils.getRandom(16, MiRandomUtils.NUMBER_LETTER);
    }
}
