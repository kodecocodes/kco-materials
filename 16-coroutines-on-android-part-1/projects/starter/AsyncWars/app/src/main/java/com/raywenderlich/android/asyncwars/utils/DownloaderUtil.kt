package com.raywenderlich.android.asyncwars.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object DownloaderUtil {

  /**
   * Function to initiate a blocking request to fetch a bitmap from a remote server
   * @return Bitmap?
   */
  fun downloadImage(): Bitmap? {
    val url = "https://image.ibb.co/iP7TDL/asyncwarslogo.png"
    val client = OkHttpClient()

    val request = Request.Builder().url(url).build()

    var response: Response? = null
    var bitmap: Bitmap? = null
    try {
      // Invokes the request immediately, and blocks until
      // the response can be processed or is in error.
      response = client.newCall(request).execute()
    } catch (e: IOException) {
      e.printStackTrace()
    }

    // Process response received
    response?.apply {
      if (isSuccessful) {
        try {
          bitmap = BitmapFactory.decodeStream(body()?.byteStream())
        } catch (e: Exception) {
          e.printStackTrace()
        }
      }

      // Close the response body
      close()
    }

    return bitmap
  }
}