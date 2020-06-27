package com.example.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.arch.core.executor.TaskExecutor
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class ImageLoader() {

    private lateinit var cache: ImageCache
    var progressBar: ProgressBar? = null
    private var executorService =
        Executors.newFixedThreadPool(5, Utils.ImageThreadFactory())
    private val handler: Handler = Handler()

    companion object {
        private lateinit var doubleCache: DoubleCache
        private var Instance: ImageLoader? = null

        @Synchronized
        fun with(context: Context): ImageLoader {
            doubleCache = DoubleCache(context)
            return Instance ?: ImageLoader().also {
                Instance = it
            }
        }
    }

    fun clearCache() {
        this.cache.clearCache()
    }

    fun load(url: String, imageView: ImageView, progressBar: ProgressBar) {
        this.progressBar = progressBar;
        load(url, imageView)
    }

    fun load(url: String, imageView: ImageView) {
        cache = doubleCache
        val cachedBitmap = cache.getCache(url)
        if (cachedBitmap != null) {
            loadImageIntoImageView(imageView, cachedBitmap)
            return
        }
        executorService.submit(PhotosLoader(ImageRequest(url, imageView)))
    }


    private fun downloadUrl(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val imageUrl = URL(url)
            val connection: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
            bitmap = BitmapFactory.decodeStream(connection.inputStream)
            connection.disconnect()
        } catch (e: Exception) {
            Log.d("Error", e.localizedMessage, e)
        }
        return bitmap
    }


    private fun loadImageIntoImageView(imageView: ImageView, bitmap: Bitmap?) {
        val scaledBitmap = Utils.scaleBitmapForLoad(bitmap, imageView.width, imageView.height)

        scaledBitmap?.let {
            imageView.setImageBitmap(scaledBitmap)
        }
        progressBar?.let {
            progressBar?.visibility = View.GONE
        }

    }



    inner class ImageRequest(var imgUrl: String, var imageView: ImageView)
    inner class PhotosLoader(private var imageRequest: ImageRequest) : Runnable {

        override fun run() {

//            val bitmap = Utils.downloadBitmapFromURL(imageRequest.imgUrl)
            val bitmap: Bitmap? = downloadUrl(imageRequest.imgUrl)
            cache.putCache(imageRequest.imgUrl, bitmap)

            val displayBitmap = DisplayBitmap(imageRequest)
            handler.post(displayBitmap)
        }
    }


    inner class DisplayBitmap(private var imageRequest: ImageRequest) : Runnable {
        override fun run() {
            loadImageIntoImageView(
                imageRequest.imageView,
                cache.getCache(imageRequest.imgUrl)
            )
        }
    }
}