package com.lixiaomi.baselib.utils;

import java.util.Random;

/**
 * @describe：生成验证码的工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2018/06/08<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
public class MiRandomUtils {
    private final static String NUMBER_STR = "0123456789";
    private final static String LETTER_LITTLE_STR = "abcdefghigklmnopqrstuvwxyz";
    private final static String LETTER_BIG_STR = "ABCDEFGHIGKLMNOPQRSTUVWXYZ";

    public final static int NUMBER = 0x1;
    public final static int LETTER_LITTLE = 0x2;
    public final static int LETTER_BIG = 0x3;
    public final static int NUMBER_LETTER = 0x4;
    public final static int LETTER = 0x5;

    public static String getRandom(int length, int randomType) {
        String randomString = getRandomString(randomType);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = new Random().nextInt(randomString.length());
            stringBuffer.append(randomString.charAt(index));
        }
        return stringBuffer.toString();
    }


    private static String getRandomString(int randomType) {
        if (randomType != NUMBER && randomType != LETTER_LITTLE && randomType != LETTER_BIG
                && randomType != NUMBER_LETTER && randomType != LETTER) {
            throw new IllegalArgumentException("randomType is error !");
        }
        String str = "";
        switch (randomType) {
            case NUMBER:
                str = NUMBER_STR;
                break;
            case LETTER_LITTLE:
                str = LETTER_LITTLE_STR;
                break;
            case LETTER_BIG:
                str = LETTER_BIG_STR;
                break;
            case NUMBER_LETTER:
                str = NUMBER_STR + LETTER_LITTLE_STR + LETTER_BIG_STR;
                break;
            case LETTER:
                str = LETTER_LITTLE_STR + LETTER_BIG_STR;
                break;
        }
        return str;
    }
}
