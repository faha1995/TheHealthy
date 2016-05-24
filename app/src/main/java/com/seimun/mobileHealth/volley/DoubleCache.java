package com.seimun.mobileHealth.volley;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/3/8
 */
public class DoubleCache implements ImageLoader.ImageCache {
    private ImageLoader.ImageCache memoryCache ;
    private ImageLoader.ImageCache diskCache;

    public DoubleCache() {
        memoryCache = new MemoryCache();
        diskCache   = new DiskCache();
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bmp = memoryCache.getBitmap(url);
        if (bmp == null) {
            bmp = diskCache.getBitmap(url);
        }
        return bmp;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        memoryCache.putBitmap(url,bitmap);
        diskCache.putBitmap(url,bitmap);
    }
}
