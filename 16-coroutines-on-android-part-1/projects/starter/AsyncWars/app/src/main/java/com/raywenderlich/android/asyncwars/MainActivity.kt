/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.asyncwars

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import com.raywenderlich.android.asyncwars.async.GetImageAsyncTask
import com.raywenderlich.android.asyncwars.async.MyIntentService
import com.raywenderlich.android.asyncwars.utils.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors


/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  companion object {
    const val FILTER_ACTION_KEY = "MY_ACTION"
  }

  enum class MethodToDownloadImage {
    Thread, AsyncTask, IntentService, Handler, HandlerThread, Executor, RxJava, Coroutine
  }

  private val imageDownloadListener = object : ImageDownloadListener {
    override fun onSuccess(bitmap: Bitmap?) {
      // Update UI with downloaded bitmap
      imageView?.setImageBitmap(bitmap)
    }
  }

  // Create an instance of MyBroadcastReceiver
  private val myReceiver = MyBroadcastReceiver(imageDownloadListener)
  private val myRunnable = MyRunnable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Setup a rotating spinner in the UI
    val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely)
    contentLoadingProgressBar.startAnimation(rotateAnimation)

    //region --------- Modify below to setup app to use a specific type of async method --------- //
    val doProcessingOnUiThread = true
    val methodToUse = MethodToDownloadImage.Thread
    //endregion

    // Setup the UI text
    if (doProcessingOnUiThread) {
      // Set the type of method used in UI
      textViewMethodUsed.text = "Calculating on UI thread: Fibonacci Number"
    } else {
      when (methodToUse) {
        MethodToDownloadImage.Thread -> setMethodBeingUsedInUi("Thread")
        MethodToDownloadImage.AsyncTask -> setMethodBeingUsedInUi("AsyncTask")
        MethodToDownloadImage.IntentService -> setMethodBeingUsedInUi("IntentService")
        MethodToDownloadImage.Handler -> setMethodBeingUsedInUi("Handler")
        MethodToDownloadImage.HandlerThread -> setMethodBeingUsedInUi("HandlerThread")
        MethodToDownloadImage.Executor -> setMethodBeingUsedInUi("Executor")
        MethodToDownloadImage.RxJava -> setMethodBeingUsedInUi("RxJava")
        MethodToDownloadImage.Coroutine -> setMethodBeingUsedInUi("Coroutine")
      }
    }

    buttonDownloadBitmap.setOnClickListener {
      // Reset the imageview
      imageView?.setImageBitmap(null)

      if (doProcessingOnUiThread) {
        // Processing on UI thread, blocking UI
        runUiBlockingProcessing()
      } else {
        when (methodToUse) {
          MethodToDownloadImage.Thread -> getImageUsingThread()
          MethodToDownloadImage.AsyncTask -> getImageUsingAsyncTask()
          MethodToDownloadImage.IntentService -> getImageUsingIntentService()
          MethodToDownloadImage.Handler -> getImageUsingHandler()
          MethodToDownloadImage.HandlerThread -> getImageUsingHandlerThread()
          MethodToDownloadImage.Executor -> getImageUsingExecutors()
          MethodToDownloadImage.RxJava -> getImageUsingRx()
          MethodToDownloadImage.Coroutine -> getImageUsingCoroutines()
        }
      }
    }
  }

  // ----------- Async Methods -----------//

  fun runUiBlockingProcessing() {
    // Processing
    showToast("Result: ${fibonacci(40)}")
  }

  fun getImageUsingThread() {
    // Download image
    val thread = Thread(myRunnable)
    thread.start()
  }

  fun getImageUsingIntentService() {
    // Download image
    val intent = Intent(this@MainActivity, MyIntentService::class.java)
    startService(intent)
  }

  fun getImageUsingExecutors() {
    // Download image
    val executor = Executors.newFixedThreadPool(4)
    executor.submit(myRunnable)
  }

  fun getImageUsingAsyncTask() {
    // Download image
    val myAsyncTask = GetImageAsyncTask(imageDownloadListener)
    myAsyncTask.execute()
  }

  fun getImageUsingHandler() {
    // Create a Handler using the main Looper
    val uiHandler = Handler(Looper.getMainLooper())

    // Create a new thread
    Thread {
      // Download image
      val bmp = DownloaderUtil.downloadImage()

      // Using the uiHandler update the UI
      uiHandler.post {
        imageView?.setImageBitmap(bmp)
      }
    }.start()
  }

  var handlerThread: HandlerThread? = null
  fun getImageUsingHandlerThread() {
    // Download image
    // Create a HandlerThread
    handlerThread = HandlerThread("MyHandlerThread")

    handlerThread?.let {
      // Start the HandlerThread
      it.start()
      // Get the Looper
      val looper = it.looper
      // Create a Handler using the obtained Looper
      val handler = Handler(looper)
      // Execute the Handler
      handler.post {
        // Download Image
        val bmp = DownloaderUtil.downloadImage()

        // Send local broadcast with the bitmap as payload
        BroadcasterUtil.sendBitmap(applicationContext, bmp)
      }
    }
  }

  var single: Disposable? = null
  fun getImageUsingRx() {
    // Download image
    single = Single.create<Bitmap> { emitter ->
      DownloaderUtil.downloadImage()?.let { bmp ->
        emitter.onSuccess(bmp)
      }
    }.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe { bmp ->
          // Update UI with downloaded bitmap
          imageView?.setImageBitmap(bmp)
        }
  }

  fun getImageUsingCoroutines() {
    // TODO: add implementation here
  }

  // Implementing the Runnable interface to implement threads.
  inner class MyRunnable : Runnable {

    override fun run() {
      // Download Image
      val bmp = DownloaderUtil.downloadImage()

      // Update UI on the UI/Main Thread with downloaded bitmap
      runOnUiThread {
        imageView?.setImageBitmap(bmp)
      }
    }
  }

  // ----------- Helper Methods -----------//
  fun fibonacci(number: Int): Long {
    return if (number == 1 || number == 2) {
      1
    } else fibonacci(number - 1) + fibonacci(number - 2)
  }

  fun setMethodBeingUsedInUi(method: String) {
    // Set the type of method used in UI
    textViewMethodUsed.text = "Download image using:$method"
  }

  // ----------- Lifecycle Methods -----------//
  override fun onStart() {
    super.onStart()
    BroadcasterUtil.registerReceiver(this, myReceiver)
  }

  override fun onStop() {
    super.onStop()
    BroadcasterUtil.unregisterReceiver(this, myReceiver)
  }

  override fun onDestroy() {
    super.onDestroy()

    // Quit and cleanup any instance of dangling HandlerThread
    handlerThread?.quit()

    // Cleanup disposable if it was created i.e. not null
    single?.dispose()
  }
}
