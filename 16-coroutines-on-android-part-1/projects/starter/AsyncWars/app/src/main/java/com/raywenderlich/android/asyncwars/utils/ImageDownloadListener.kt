package com.raywenderlich.android.asyncwars.utils

import android.graphics.Bitmap

interface ImageDownloadListener {
  fun onSuccess(bitmap: Bitmap?)
}