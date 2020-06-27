package com.example.imageloader

import android.graphics.Bitmap

 interface ImageCache {
    fun putCache(url:String,bitmap: Bitmap?)
    fun getCache(url:String) : Bitmap?
    fun clearCache()
}