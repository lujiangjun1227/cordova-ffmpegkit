package com.iknow.android;

import android.content.Context;
import android.util.Log;
import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.ReturnCode;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import iknow.android.utils.BaseUtils;

public class ZApplication {

    private static boolean isInit = false;

    public static void init(Context context) {

        if (isInit == false) {
            BaseUtils.init(context);
            initImageLoader(context);
            initFFmpegBinary();
        }
        isInit = true;

    }

    public static void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 10);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCache(new LRULimitedMemoryCache(memoryCacheSize))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private static void initFFmpegBinary() {
        FFmpegKit.executeAsync("-version", session -> {
            ReturnCode returnCode = session.getReturnCode();
            if (ReturnCode.isSuccess(returnCode)) {
                Log.d("FFmpegLoader", "FFmpegKit initialized successfully.");
            } else {
                Log.d("FFmpegLoader", "Failed to initialize FFmpegKit.");
            }
        });
    }
}
