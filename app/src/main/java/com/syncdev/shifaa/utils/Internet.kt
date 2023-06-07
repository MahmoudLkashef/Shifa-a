package com.syncdev.shifaa.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Internet {
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false

        val networkInfo =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return networkInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}