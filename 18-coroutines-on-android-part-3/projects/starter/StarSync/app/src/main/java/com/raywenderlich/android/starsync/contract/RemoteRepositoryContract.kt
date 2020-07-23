package com.raywenderlich.android.starsync.contract

import com.raywenderlich.android.starsync.repository.model.People

interface RemoteRepositoryContract {

  fun getDataUsingCallback(itemListCallback: ItemListCallback)

  suspend fun getDataUsingCoroutines(): List<People>
}