package com.raywenderlich.android.starsync.ui.mainscreen

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.raywenderlich.android.starsync.contract.DataRepositoryContract
import com.raywenderlich.android.starsync.contract.PresenterContract
import com.raywenderlich.android.starsync.contract.ViewContract
import com.raywenderlich.android.starsync.repository.model.People
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivityPresenter(var view: ViewContract?, var repository: DataRepositoryContract?) :
    PresenterContract, CoroutineScope, DefaultLifecycleObserver {

  private val coroutineJob = Job()
  override val coroutineContext: CoroutineContext = Dispatchers.Main + coroutineJob

  init {
    view?.hideLoading()
  }

  override fun cleanup() {
    // Cancel all coroutines running in this context
    coroutineContext.cancel()

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
    fetchUsingCoroutines()
  }

  private fun fetchUsingCoroutines() {
    launch {
      try {

        // TODO: Add a log with msg 'launch executed'
        // Fetch from local first, using the background thread
        var itemList = withContext(Dispatchers.IO) {
          // TODO: Add a log with msg 'Fetching from local
          repository?.getDataFromLocal()
        }

        // TODO: Force a Runtime crash here (for demonstrating try catch behavior)

        // TODO: Add a log with msg 'Got items from local'
        // When done, update the UI
        updateData(itemList, "Local DB")

        // try fetching from remote next
        itemList = withContext(Dispatchers.IO) {
          repository?.getDataFromRemoteUsingCoroutines()
        }

        // TODO: Add a log with msg 'Got items from remote'
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

        saveDataUsingCoroutines(it)

        // Hide loading animation
        view?.hideLoading()

        // Update view
        view?.updateWithData(it)
      }
    }
  }

  private fun saveDataUsingCoroutines(itemList: List<People>) {

    // TODO: Setup CoroutineExceptionHandler for launch coroutine here
    launch(Dispatchers.IO) {

      // TODO: Force a Runtime crash here (for demonstrating CoroutineExceptionHandler behavior)

      // save items to db
      repository?.saveData(itemList)
    }
  }

  override fun onResume(owner: LifecycleOwner) {
    super.onResume(owner)
    getData()
  }

  override fun onDestroy(owner: LifecycleOwner) {
    cleanup()
    super.onDestroy(owner)
  }

  private val handler = CoroutineExceptionHandler { _, throwable ->
    handleError(Exception(throwable.message))
  }

  //TODO: Anko implementation
}