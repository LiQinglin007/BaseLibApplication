package com.lixiaomi.baselib.utils;

/**
 * @describe：检查字符串工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2017/10/10<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class CheckStringEmptyUtils {
    public final static String LIST_SUCCESS = "NOHAVEEMPTY";

    /**
     * 检查字符串是不是空的
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) ? true : false;
    }

    /**
     * 检查两个字符串是否相同
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isEqual(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        } else {
            return str1.equals(str2) ? true : false;
        }
    }

    /**
     * 检查字符串是否为空串
     *
     * @param beans
     * @return
     */
    public static String checkStringList(CheckStringBean... beans) {
        for (CheckStringBean bean : beans) {
            if (isEmpty(bean.content)) {
                return bean.prompt;
            }
        }
        return LIST_SUCCESS;
    }


    /**
     * 检查字符串长度
     *
     * @param beans
     * @return
     */
    public static String checkStringLengList(CheckStringLengBean... beans) {
        for (CheckStringLengBean bean : beans) {
            if (isEmpty(bean.content)) {
                return bean.prompt;
            } else {
                if (bean.content.length() < bean.minLeng || bean.content.length() > bean.maxLeng) {
                    return bean.prompt;
                }
            }
        }
        return LIST_SUCCESS;
    }


    /**
     * 检查是否为空的bean
     */
    public static class CheckStringBean {
        String content;
        String prompt;

        public CheckStringBean(String content, String prompt) {
            this.content = content;
            this.prompt = prompt;
        }
    }

    /**
     * 检查字符串长度的bean
     */
    public static class CheckStringLengBean extends CheckStringBean {
        int minLeng;
        int maxLeng;

        public CheckStringLengBean(String content, String prompt, int minLeng, int maxLeng) {
            super(content, prompt);
            this.minLeng = minLeng;
            this.maxLeng = maxLeng;
        }
    }


}
