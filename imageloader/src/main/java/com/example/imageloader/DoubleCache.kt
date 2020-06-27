package com.example.imageloader

import android.content.Context
import android.graphics.Bitmap

 class DoubleCache(context: Context) : ImageCache {

    private val memoryCache = MemoryCache()
    private val diskCache = DiskCache(context)

    override fun putCache(url: String, bitmap: Bitmap?) {
        memoryCache.putCache(url, bitmap)
        diskCache.putCache(url, bitmap)
    }

    override fun getCache(url: String): Bitmap? {
        return memoryCache.getCache(url) ?: diskCache.getCache(url)
    }

    override fun clearCache() {
        memoryCache.clearCache()
        diskCache.clearCache()
    }

}