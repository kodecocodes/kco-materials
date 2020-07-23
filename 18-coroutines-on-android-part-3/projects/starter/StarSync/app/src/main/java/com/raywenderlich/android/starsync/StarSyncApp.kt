package com.raywenderlich.android.starsync

import android.app.Application
import android.util.Log

class StarSyncApp : Application() {

  override fun onCreate() {
    super.onCreate()

    // TODO: Enable Debugging for Kotlin Coroutines

    // Setup handler for uncaught exceptions.
    Thread.setDefaultUncaughtExceptionHandler { _, e -> Log.e("UncaughtExpHandler", e.message) }
  }
}
