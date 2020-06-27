package com.example.greedygameassesment.utils

import android.content.Context
import android.net.ConnectivityManager

class AppUtils {
    companion object {
        private var instance: AppUtils = AppUtils()

        @Synchronized
        fun getInstance(): AppUtils {
            return instance
        }
    }

    //To Check the internet connection
    fun isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}