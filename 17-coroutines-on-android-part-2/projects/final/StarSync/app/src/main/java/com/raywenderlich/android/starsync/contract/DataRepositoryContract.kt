package com.raywenderlich.android.starsync.contract

import com.raywenderlich.android.starsync.repository.model.People

interface DataRepositoryContract {

  fun getDataFromLocal(): List<People>

  fun getDataFromRemoteUsingCallback(itemListCallback: ItemListCallback)

  suspend fun getDataFromRemoteUsingCoroutines(): List<People>

  fun saveData(itemList: List<People>)
}