package com.example.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.imageloader.disklrucache.DiskLruCache
import java.io.*

class DiskCache(context: Context) : ImageCache {
    private var cache: DiskLruCache = DiskLruCache.open(context.cacheDir, 1, 1, 10 * 1024 * 1024)

    override fun putCache(url: String, bitmap: Bitmap?) {
        val key: String = url
        var editor: DiskLruCache.Editor? = null
        try {
            editor = cache.edit(key.hashCode().toString())
            if (editor == null) {
                return
            }
            if (bitmap?.let { writeBitmapToFile(it, editor) }!!) {
                cache.flush()
                editor.commit()
            } else {
                editor.abort()
            }
        } catch (e: IOException) {
            try {
                editor?.abort()
            } catch (ignored: IOException) {
            }
        }
    }

    override fun getCache(url: String): Bitmap? {
        val snapshot: DiskLruCache.Snapshot? = cache.get(url.hashCode().toString())
        return if (snapshot != null) {
            val inputStream: InputStream = snapshot.getInputStream(0)
            val buffIn = BufferedInputStream(inputStream, 8 * 1024)
            BitmapFactory.decodeStream(buffIn)
        } else {
            null
        }
    }
    private fun writeBitmapToFile(bitmap: Bitmap, editor: DiskLruCache.Editor): Boolean {
        var out: OutputStream? = null
        try {
            out = BufferedOutputStream(editor.newOutputStream(0), 8 * 1024)
            return bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        } finally {
            out?.close()
        }
    }

    override fun clearCache() {
        cache.delete()
    }
}