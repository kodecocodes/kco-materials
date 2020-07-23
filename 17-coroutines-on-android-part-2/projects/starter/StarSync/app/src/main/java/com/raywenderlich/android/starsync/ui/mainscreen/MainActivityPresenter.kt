package com.raywenderlich.android.starsync.ui.mainscreen

import com.raywenderlich.android.starsync.contract.DataRepositoryContract
import com.raywenderlich.android.starsync.contract.ItemListCallback
import com.raywenderlich.android.starsync.contract.PresenterContract
import com.raywenderlich.android.starsync.contract.ViewContract
import com.raywenderlich.android.starsync.repository.model.People
import com.raywenderlich.android.starsync.utils.BACKGROUND_THREAD
import com.raywenderlich.android.starsync.utils.FetchFromLocalDbTask
import com.raywenderlich.android.starsync.utils.ProcessUsing
import kotlinx.coroutines.*

class MainActivityPresenter(var view: ViewContract?, var repository: DataRepositoryContract?) :
    PresenterContract {

  private val processingUsing = ProcessUsing.BackgroundThread

  private val coroutineJob = Job()

  private val coroutineScope = CoroutineScope(coroutineJob)

  init {
    view?.hideLoading()
  }

  override fun cleanup() {
    // Cancel all coroutines running in this context
    coroutineJob.cancel()

    this.view = null
    this.repository = null
  }

  override fun handleError(e: Exception) {
    // Hide loading animation
    view?.hideLoading()

    // prompt in view
    view?.prompt(e.message)
  }

  override fun getData() {
    // Start loading animation
    view?.showLoading()

    // Fetch Data
    fetchData()
  }

  private fun fetchData() {
    when (processingUsing) {
      // 1
      ProcessUsing.BackgroundThread -> fetchUsingBackgroundThreads()

      // 2
      ProcessUsing.Coroutines -> fetchUsingCoroutines()
    }
  }

  private fun fetchUsingBackgroundThreads() {
    // Fetch from local first, using the background thread
    FetchFromLocalDbTask(repository, object : ItemListCallback {
      override fun onError(e: Exception) {
        handleError(e)
      }

      override fun onSuccess(itemList: List<People>?) {
        // When done, update the UI
        updateData(itemList, "Local DB")
      }
    }).execute()

    // try fetching from remote next
    repository?.getDataFromRemoteUsingCallback(object : ItemListCallback {
      override fun onError(e: Exception) {
        handleError(e)
      }

      override fun onSuccess(itemList: List<People>?) {
        // When done, update the UI
        updateData(itemList, "Remote Server")
      }
    })
  }

  private fun fetchUsingCoroutines() {
    coroutineScope.launch {
      try {
        // Fetch from local first, using the background thread
        var itemList = withContext(Dispatchers.IO) {
          repository?.getDataFromLocal()
        }

        // When done, update the UI
        updateData(itemList, "Local DB")

        // try fetching from remote next
        itemList = withContext(Dispatchers.IO) {
          repository?.getDataFromRemoteUsingCoroutines()
        }

        // When done, update the UI
        updateData(itemList, "Remote Server")
      } catch (e: Exception) {
        handleError(e)
      }
    }
  }

  override fun updateData(itemList: List<People>?, fromString: String) {
    itemList?.let {
      if (it.isNotEmpty()) {
        //  Prompt in view about the source of data
        view?.prompt("Loading data from $fromString")

        when (processingUsing) {
          // 1
          ProcessUsing.BackgroundThread -> saveDataUsingBackgroundThreads(it)

          // 2
          ProcessUsing.Coroutines -> saveDataUsingCoroutines(it)
        }

        // Hide loading animation
        view?.hideLoading()

        // Update view
        view?.updateWithData(it)
      }
    }
  }

  private fun saveDataUsingCoroutines(itemList: List<People>) {
    coroutineScope.launch(Dispatchers.IO) {
      // save items to db
      repository?.saveData(itemList)
    }
  }

  private fun saveDataUsingBackgroundThreads(itemList: List<People>) {
    BACKGROUND_THREAD.submit {
      // save items to db
      repository?.saveData(itemList)
    }
  }
}