package com.lixiaomi.baselib.utils;

import android.content.Context;


/**
 * @describe：通过反射,根据资源名称,拿到资源id<br>
 * @author：Xiaomi<br>
 * @createTime：2019/5/11<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class GetResId {

    /**
     * 例子:R.drawable.icon_test
     *
     * @param context
     * @param className 资源类型:drawable/layout等,例子这里传drawable
     * @param name      资源名称,例子这里传icon_test
     * @return
     */
    public static int getIdByName(Context context, String className, String name) {
        String packageName = context.getPackageName();
        Class r = null;
        int id = 0;
        try {
            //拿到R文件Class文件
            r = Class.forName(packageName + ".R");
            //返回类定义的公共的内部类,以及从父类、父接口那里继承来的内部类
            Class[] classes = r.getClasses();
            Class desireClass = null;
            /**
             *  [class com.tencent.qcloud.timchat.R$styleable, class com.tencent.qcloud.timchat.R$style, class com.tencent.qcloud.timchat.R$string,
             *  class com.tencent.qcloud.timchat.R$raw, class com.tencent.qcloud.timchat.R$mipmap, class com.tencent.qcloud.timchat.R$layout
             */
            for (int i = 0; i < classes.length; ++i) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;
                }
            }
            if (desireClass != null) {
                id = desireClass.getField(name).getInt(desireClass);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return id;
    }
}
