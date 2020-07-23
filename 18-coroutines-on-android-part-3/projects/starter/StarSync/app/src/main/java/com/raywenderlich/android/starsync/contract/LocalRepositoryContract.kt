package com.raywenderlich.android.starsync.contract

import com.raywenderlich.android.starsync.repository.model.People

interface LocalRepositoryContract {
  fun getData(): List<People>

  fun saveData(itemList: List<People>)
}