package com.lixiaomi.baselib.utils.loadImageUtils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;


/**
 * @describe：Glide<br>
 * @author：Xiaomi<br>
 * @createTime：2018/6/5<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
@GlideModule
public final class CustomAppGlideModule extends AppGlideModule {

    /**
     * 通过GlideBuilder设置默认的结构(Engine,BitmapPool ,ArrayPool,MemoryCache等等).
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //重新设置内存限制 10M
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
        //重新设置内存限制 10M
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
        //bitmap
        builder.setBitmapPool(new LruBitmapPool(1024 * 1024 * 30));
        //磁盘缓存限制
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 1024 * 1024 * 100));

    }


    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//        registry.append(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    /**
     * 清单解析的开启
     * <p>
     * 这里不开启，避免添加相同的modules两次
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}