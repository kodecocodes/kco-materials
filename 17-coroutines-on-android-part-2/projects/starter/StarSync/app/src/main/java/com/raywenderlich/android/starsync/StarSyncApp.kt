package com.raywenderlich.android.starsync

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy.KEEP
import androidx.work.NetworkType.UNMETERED
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.raywenderlich.android.starsync.repository.RefreshRemoteRepo
import java.util.concurrent.TimeUnit

class StarSyncApp : Application() {

  override fun onCreate() {
    super.onCreate()

    setupWorkManagerJob()
  }

  private fun setupWorkManagerJob() {
    val constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .setRequiredNetworkType(UNMETERED)
        .build()

    // Attempt to run every day
    val work = PeriodicWorkRequest
        .Builder(RefreshRemoteRepo::class.java, 1, TimeUnit.DAYS)
        .setConstraints(constraints)
        .build()

    // Enqueue the work to WorkManager, keeping any previously scheduled jobs for the same work.
    WorkManager.getInstance()
        .enqueueUniquePeriodicWork(RefreshRemoteRepo::class.java.name, KEEP, work)
  }
}
