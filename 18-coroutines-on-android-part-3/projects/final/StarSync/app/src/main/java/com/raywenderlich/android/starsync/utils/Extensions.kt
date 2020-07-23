package com.raywenderlich.android.starsync.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresPermission

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}


@RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isOnline(): Boolean {
  val connectivityManager = this
      .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
  connectivityManager?.apply {
    val netInfo = activeNetworkInfo
    netInfo?.let {
      if (it.isConnected) return true
    }
  }
  return false
}

fun View.show() {
  this.visibility = View.VISIBLE
}

fun View.hide() {
  this.visibility = View.GONE
}


// Log Coroutines
fun logCoroutineInfo(msg: String) = println("Running on: [${Thread.currentThread().name}] | $msg")

