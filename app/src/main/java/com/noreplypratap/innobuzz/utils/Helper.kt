package com.noreplypratap.innobuzz.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.noreplypratap.innobuzz.service.TrackerService


fun Context.startTrackerService(
    callback: ((intent: Intent) -> Any)? = null
) {
    val intent = Intent(this, TrackerService::class.java)
    callback?.let {
        callback(intent)
    }
    startForegroundService(intent)
}

fun Context.isOnline() : Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}

fun logger(msg: String) {
    Log.d(Constants.TAG,msg)
}
fun Context.showToast(msg: String) {
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}
