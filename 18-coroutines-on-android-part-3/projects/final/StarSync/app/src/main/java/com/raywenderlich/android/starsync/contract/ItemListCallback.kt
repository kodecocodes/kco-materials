package com.raywenderlich.android.starsync.contract

import com.raywenderlich.android.starsync.repository.model.People

interface ItemListCallback {
  fun onError(e: Exception)
  fun onSuccess(itemList: List<People>?)
}