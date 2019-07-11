package com.lixiaomi.baselib.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Map;

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/7/4<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MiJsonUtil {
    private static Gson mGson;

    static {
        mGson = new GsonBuilder()
                //显示null属性
                .serializeNulls()
                //禁止html转义
                .disableHtmlEscaping()
                .create();
    }


    public static Gson getGson() {
        return mGson;
    }


    public static String getJson(Object object) {
        return mGson.toJson(object);
    }

    public static <T> T getClass(String json, Class<T> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    public static Map getMap(String json) {
        return mGson.fromJson(json, Map.class);
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> getClassList(String json, Class<T> cls) {
        ArrayList<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(mGson.fromJson(elem, cls));
        }
        return list;
    }
}
