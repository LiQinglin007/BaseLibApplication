package com.lixiaomi.baselib.utils.loadImageUtils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lixiaomi.baselib.R;
import com.lixiaomi.baselib.config.AppConfigInIt;
import com.lixiaomi.baselib.config.AppConfigType;
import com.lixiaomi.baselib.net.HttpConfig;

import java.util.Map;


/**
 * @describe：加载图片工具类<br>
 * @author：Xiaomi<br>
 * @createTime：2019/7/5<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
public class MiLoadImageUtil {

    /**
     * 拼接图片地址
     *
     * @param url
     * @return
     */
    private static String getImgUrl(String url) {
        if (url.contains("http://") || url.contains("https://")) {
            return url;
        } else {
            return ((HttpConfig) AppConfigInIt.getConfiguration(AppConfigType.HTTP_CONFIG)).getHTTP_BASE_API() + url;
        }
    }

    /**
     * 给图片添加请求头
     * 如果请求地址需要添加请求头，那先调用次方法获取url,在调用其他loadImage方法把getHeadUrl()方法的返回值当成url传进去
     *
     * @param header 请求头
     * @param url    请求地址
     * @return
     */
    public static GlideUrl getHeadUrl(final Map<String, String> header, String url) {
        GlideUrl glideUrl = new GlideUrl((getImgUrl(url)), new Headers() {
            @Override
            public Map<String, String> getHeaders() {
                return header;
            }
        });
        return glideUrl;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, Object url, ImageView imageView) {
        GlideApp
                .with(context)
                //加载地址
                .load(url instanceof String ? getImgUrl((String) url) : url)
                //缓存策略
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //失败图片
                .error(R.drawable.errorpic)
                //加载成功前的图片
                .placeholder(R.drawable.errorpic)
                //文件或地址为null的图片
                .fallback(R.drawable.errorpic)
                //加载到哪里
                .into(imageView);
    }

    /**
     * 加载图片 跳过缓存
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageSkipCache(Context context, Object url, ImageView imageView) {
        GlideApp
                .with(context)
                //加载地址
                .load(url instanceof String ? getImgUrl((String) url) : url)
                //缓存策略:不缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //跳过内存缓存
                .skipMemoryCache(true)
                //失败图片
                .error(R.drawable.errorpic)
                //加载成功前的图片
                .placeholder(R.drawable.errorpic)
                //文件或地址为null的图片
                .fallback(R.drawable.errorpic)
                //加载到哪里
                .into(imageView);
    }


    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param errorImgId   加载失败显示的图片
     * @param placeholerId 预览图片，如果url或文件为null,会继续显示该图片
     */
    public static void loadImage(Context context, Object url, ImageView imageView, int errorImgId, int placeholerId) {
        GlideApp
                .with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                .error(errorImgId)
                .placeholder(placeholerId)
                //缓存策略
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param errorImgId   加载失败显示的图片
     * @param placeholerId 预览图片，如果url或文件为null,会继续显示该图片
     */
    public static void loadImageSkipCache(Context context, Object url, ImageView imageView, int errorImgId, int placeholerId) {
        GlideApp
                .with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                .error(errorImgId)
                //缓存策略:不缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //跳过内存缓存
                .skipMemoryCache(true)
                .placeholder(placeholerId)
                .into(imageView);
    }


    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param imageView 图片
     * @param radius    圆角角度
     */
    public static void loadImage(Context context, Object url, ImageView imageView, int radius) {
        GlideApp
                .with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                //缓存策略
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.errorpic)
                .placeholder(R.drawable.errorpic)
                .fallback(R.drawable.errorpic)
                .transforms(new RoundedCorners(radius))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param radius       圆角角度
     * @param errorImgId   加载失败显示的图片
     * @param placeholerId 预览图片，如果url或文件为null,会继续显示该图片
     */
    public static void loadImage(Context context, Object url, ImageView imageView, int radius, int errorImgId, int placeholerId) {
        GlideApp
                .with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                //缓存策略
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errorImgId)
                .placeholder(placeholerId)
                .transforms(new RoundedCorners(radius))
                .into(imageView);
    }


    /**
     * 圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageCircle(Context context, Object url, ImageView imageView) {
        GlideApp
                .with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                //缓存策略
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.errorpic)
                .placeholder(R.drawable.errorpic)
                .fallback(R.drawable.errorpic)
                .transform(new CircleCrop())
                .into(imageView);
    }

    /**
     * 圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param errorImgId   加载失败显示的图片
     * @param placeholerId 预览图片，如果url或文件为null,会继续显示该图片
     */
    public static void loadImageCircle(Context context, Object url, ImageView imageView, int errorImgId, int placeholerId) {
        GlideApp
                .with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                //缓存策略
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errorImgId)
                .placeholder(placeholerId)
                .transform(new CircleCrop())
                .into(imageView);
    }


    /**
     * 加载图片到view的背景
     *
     * @param context
     * @param view
     * @param url
     */
    public static void loadImageToBackGround(final Context context, final View view, String url) {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                view.setBackground(resource);
            }
        };
        GlideApp.with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(R.drawable.errorpic)
                .placeholder(R.drawable.errorpic)
                .fallback(R.drawable.errorpic)
                .into(simpleTarget);
    }

    /**
     * 加载图片到view的背景
     *
     * @param context
     * @param view
     * @param url
     * @param errorImgId   加载失败显示的图片
     * @param placeholerId 预览图片，如果url或文件为null,会继续显示该图片
     */
    public static void loadImageToBackGround(final Context context, final View view, String url, int errorImgId, int placeholerId) {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                view.setBackground(resource);
            }
        };
        GlideApp.with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .error(errorImgId)
                .placeholder(placeholerId)
                .into(simpleTarget);
    }

    /**
     * 加载图片到view的背景，跳过缓存
     *
     * @param context
     * @param view
     * @param url
     */
    public static void loadImageToBackGroundSkipCache(final Context context, final View view, String url) {
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                view.setBackground(resource);
            }
        };
        GlideApp.with(context)
                .load(url instanceof String ? getImgUrl((String) url) : url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .error(R.drawable.errorpic)
                .placeholder(R.drawable.errorpic)
                .fallback(R.drawable.errorpic)
                .into(simpleTarget);
    }
}
