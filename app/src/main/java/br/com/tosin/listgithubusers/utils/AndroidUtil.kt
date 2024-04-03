package br.com.tosin.listgithubusers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun checkIsOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    return if (connectivityManager.activeNetwork != null) {
        val nw = connectivityManager.activeNetwork
        if (connectivityManager.getNetworkCapabilities(nw) != null) {
            val actNw = connectivityManager.getNetworkCapabilities(nw)!!
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                // for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                // for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            false
        }
    } else {
        false
    }
}
