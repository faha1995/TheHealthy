package com.seimun.mobileHealth.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/3/8
 */
public class MemoryCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> caches;

    public MemoryCache() {
        initMemoryCache();
    }

    private void initMemoryCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 4;
        caches = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String url, Bitmap bmp) {
                return bmp.getRowBytes() * bmp.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return caches.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        caches.put(url,bitmap);
    }
}
