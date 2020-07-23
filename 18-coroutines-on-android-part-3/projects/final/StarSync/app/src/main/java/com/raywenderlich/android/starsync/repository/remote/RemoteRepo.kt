package com.raywenderlich.android.starsync.repository.remote

import android.content.Context
import com.raywenderlich.android.starsync.contract.ItemListCallback
import com.raywenderlich.android.starsync.contract.RemoteRepositoryContract
import com.raywenderlich.android.starsync.repository.model.BasePeople
import com.raywenderlich.android.starsync.repository.model.People
import com.raywenderlich.android.starsync.utils.isOnline
import com.raywenderlich.android.starsync.utils.logCoroutineInfo
import retrofit2.Call
import retrofit2.Response

class RemoteRepo(val context: Context) : RemoteRepositoryContract {

  private val retrofitService = RemoteApi.starWarsService

  override suspend fun getDataUsingCoroutines(): List<People> {
    return if (context.isOnline()) {

      val request = retrofitService.getUsingCoroutines()
      logCoroutineInfo("Fetching from remote")
      val response = request.await()
      response.results ?: emptyList()
    } else {
      emptyList()
    }
  }

  override fun getDataUsingCallback(itemListCallback: ItemListCallback) {

    return if (context.isOnline()) {
      val request = retrofitService.getUsingEnqueue()

      request.enqueue(object : retrofit2.Callback<BasePeople> {
        override fun onFailure(call: Call<BasePeople>, t: Throwable) {
          itemListCallback.onError(Exception(t))
        }

        override fun onResponse(call: Call<BasePeople>, response: Response<BasePeople>) {
          if (response.isSuccessful) {
            itemListCallback.onSuccess(response.body()?.results)
          }
        }
      })
    } else {
      // Send success, with empty list when not connected to internet
      itemListCallback.onSuccess(emptyList())
    }
  }
}