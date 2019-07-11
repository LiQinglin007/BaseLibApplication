package com.lixiaomi.baselib.utils;

import android.content.SharedPreferences;

import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;


/**
 * @describe：SharedPreferences工具类(需要在AppConfig中配置)<br>
 * @author：Xiaomi<br>
 * @createTime：2016/06/11<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */

public class PreferenceUtils {

    private static SharedPreferences getSharedPreferences() {
        SharedPreferences configuration = AppConfigInIt.getConfiguration(AppConfigType.SHARED_PREFERENCES);
        return configuration;
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.edit();
        } else {
            return null;
        }
    }

    /**
     * 取boolean
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static boolean getBoolean(String key, boolean devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, devalue);
        } else {
            return devalue;
        }
    }

    /**
     * 存boolean
     *
     * @param key   键
     * @param value 值
     */
    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putBoolean(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 存Float
     *
     * @param key   键
     * @param value 值
     */
    public static void setFloat(String key, float value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putFloat(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 取Float
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static Float getFloat(String key, float devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, devalue);
        } else {
            return null;
        }
    }

    /**
     * 存Long
     *
     * @param key   键
     * @param value 值
     */
    public static void setLong(String key, long value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putLong(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 取Long
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static Long getLong(String key, long devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, devalue);
        } else {
            return null;
        }
    }

    /**
     * 存String
     *
     * @param key   键
     * @param value 值
     */
    public static void setString(String key, String value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putString(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 取String
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static String getString(String key, String devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, devalue);
        } else {
            return null;
        }
    }

    /**
     * 取int
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static int getInt(String key, int devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, devalue);
        } else {
            return devalue;
        }
    }

    /**
     * 存int
     *
     * @param key   键
     * @param value 值
     */
    public static void setInt(String key, int value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putInt(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 清空一个
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.remove(key);
            sharedPreferencesEditor.commit();
        }
    }
}
