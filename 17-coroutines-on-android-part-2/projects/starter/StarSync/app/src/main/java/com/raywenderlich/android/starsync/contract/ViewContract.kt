package com.raywenderlich.android.starsync.contract

import com.raywenderlich.android.starsync.repository.model.People

interface ViewContract {
  fun updateWithData(itemList: List<People>)
  fun showLoading()
  fun hideLoading()
  fun prompt(string: String?)
}