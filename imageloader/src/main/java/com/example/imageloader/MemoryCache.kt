package com.example.imageloader

import android.graphics.Bitmap
import android.util.LruCache

class MemoryCache : ImageCache {

    private val cache: LruCache<String, Bitmap>

    init {
        val maxMemory: Long = Runtime.getRuntime().maxMemory() / 1024
        val cacheSize: Int = (maxMemory / 4).toInt()

        cache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }

        }
    }

    override fun putCache(url: String, bitmap: Bitmap?) {
        cache.put(url,bitmap)
    }

    override fun getCache(url: String): Bitmap? {
        return cache.get(url)
    }

    override fun clearCache() {
        cache.evictAll()
    }
}