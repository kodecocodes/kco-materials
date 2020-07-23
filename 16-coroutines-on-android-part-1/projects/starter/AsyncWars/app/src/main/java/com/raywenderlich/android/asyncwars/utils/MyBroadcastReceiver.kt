package com.raywenderlich.android.asyncwars.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap

class MyBroadcastReceiver(val imageDownloadListener: ImageDownloadListener) : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    val bmp = intent.getParcelableExtra<Bitmap>("bitmap")

    // Pass it to the listener
    imageDownloadListener.onSuccess(bmp)
  }
}
