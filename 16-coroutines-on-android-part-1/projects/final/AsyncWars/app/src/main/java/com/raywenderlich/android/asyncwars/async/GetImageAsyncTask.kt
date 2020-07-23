package com.raywenderlich.android.asyncwars.async

import android.graphics.Bitmap
import android.os.AsyncTask
import com.raywenderlich.android.asyncwars.utils.DownloaderUtil
import com.raywenderlich.android.asyncwars.utils.ImageDownloadListener

class GetImageAsyncTask(val imageDownloadListener: ImageDownloadListener) :
    AsyncTask<String, Void, Bitmap>() {


  // This executes on the background thread
  override fun doInBackground(vararg p0: String?): Bitmap? {
    // Download Image
    return DownloaderUtil.downloadImage()
  }

  // This executes on the UI thread
  override fun onPostExecute(bmp: Bitmap?) {
    super.onPostExecute(bmp)
    if (isCancelled) {
      return
    }

    // Pass it to the listener
    imageDownloadListener.onSuccess(bmp)

    // Cancel this async task after everything is done.
    cancel(false)
  }
}