package com.raywenderlich.android.starsync.utils

import android.os.AsyncTask
import com.raywenderlich.android.starsync.contract.DataRepositoryContract
import com.raywenderlich.android.starsync.contract.ItemListCallback
import com.raywenderlich.android.starsync.repository.model.People

class FetchFromLocalDbTask(val repository: DataRepositoryContract?,
    private val itemListCallback: ItemListCallback) : AsyncTask<Void, Void, List<People>>() {

  override fun onPostExecute(result: List<People>?) {
    super.onPostExecute(result)

    // Return callback
    itemListCallback.onSuccess(result)

    // Stop the task
    cancel(true)
  }

  override fun doInBackground(vararg params: Void?): List<People> {
    return repository?.getDataFromLocal() ?: emptyList()
  }
}