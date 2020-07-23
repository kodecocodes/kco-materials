package com.raywenderlich.android.starsync.contract

import com.raywenderlich.android.starsync.repository.model.People

interface PresenterContract {
  fun cleanup()
  fun getData()
  fun updateData(itemList: List<People>?, fromString: String)
  fun handleError(e: Exception)
}