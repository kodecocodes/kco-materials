package com.raywenderlich.android.starsync.repository

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raywenderlich.android.starsync.repository.local.LocalRepo
import com.raywenderlich.android.starsync.repository.remote.RemoteRepo

// Worker job to refresh repository data from the network while the app is in the background.
class RefreshRemoteRepo(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

  // Processing for the worker
  override suspend fun doWork(): Result {
    return refreshData()
  }


  // Refresh data from the network using [DataRepository]
  @WorkerThread
  suspend fun refreshData(): Result {

    val localRepo = LocalRepo(applicationContext)
    val remoteRepo = RemoteRepo(applicationContext)
    val repository = DataRepository(localRepo, remoteRepo)

    return try {
      // Fetch from remote first
      val itemLists = repository.getDataFromRemoteUsingCoroutines()

      // Save to local database
      repository.saveData(itemLists)

      // Signal successful completion
      Result.success()
    } catch (error: Exception) {

      // Signal failed
      Result.failure()
    }
  }
}
