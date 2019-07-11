package com.lixiaomi.baselib.ui.Loading;

import android.content.Context;

import com.lixiaomi.baselib.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * @describe：改进LoadingVeiw<br>
 * @author：Xiaomi<br>
 * @createTime：2018/3/21<br>
 * @remarks：统一修改变量命名规则<br>
 * @changeTime:2019/2/11<br>
 */
final class LoaderCreator {
    private static final WeakHashMap<String, Indicator> LOADING_MAP = new WeakHashMap<>();

    static AVLoadingIndicatorView create(String type, Context context) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        //这里写一个默认的样式，来保证getIndicator(defaultType)返回回来的不为bull
        String defaultType = LoaderStyle.BallPulseIndicator.name();
        if (type != null) {
            defaultType = type;
        }
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(defaultType) == null) {
            final Indicator mIndicator = getIndicator(defaultType);
            LOADING_MAP.put(defaultType, mIndicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(defaultType));
        avLoadingIndicatorView.setIndicatorColor(context.getResources().getColor(R.color.color_51D8BA));
//        avLoadingIndicatorView.setIndicator(new BallPulseIndicator());

        return avLoadingIndicatorView;
    }

    //这是使用反射机制
    //getIndicator()传进去的这个name是AVLoadingIndicatorView中的type，然后所有的type放在一个枚举类里边
    //比如说BallPulseIndicator这个类型
    //传进来的就是"BallPulseIndicator"这个串
    //然后把这个串和所在的包名称拼接起来
    //然后用CLASS.forName这个方法转换成
    //com.wang.avi.indicators.BallPulseIndicator  这个类
    //然后就能拿到loader的类型了
    //然后加载到那个LOADING_MAP里边，进行缓存
    //然后调用avLoadingIndicatorView.setIndicator()这个方法来设置loader的类型
    //经过上述的操作，就相当于做了注释的代码
    //avLoadingIndicatorView.setIndicator(new BallPulseIndicator());
    //这么做的愿意，是因为AVLoadingIndicatorView使用了反射机制，在每次使用的时候都进行反射，这样浪费资源，所以才这样操作

    private static Indicator getIndicator(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        final StringBuilder stringBuilder = new StringBuilder();
        if (!name.contains(".")) {
            //拿到包名
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            stringBuilder.append(defaultPackageName).append(".indicators.");
        }
        stringBuilder.append(name);
        final Class<?> drawableClass = Class.forName(stringBuilder.toString());
        return (Indicator) drawableClass.newInstance();
    }

}
