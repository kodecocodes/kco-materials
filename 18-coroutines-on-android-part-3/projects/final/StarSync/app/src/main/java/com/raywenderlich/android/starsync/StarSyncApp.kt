package com.raywenderlich.android.starsync

import android.app.Application
import android.util.Log

class StarSyncApp : Application() {

  override fun onCreate() {
    super.onCreate()

    // Enable Debugging for Kotlin Coroutines
    System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")

    // Setup handler for uncaught exceptions.
    Thread.setDefaultUncaughtExceptionHandler { _, e -> Log.e("UncaughtExpHandler", e.message) }
  }
}
