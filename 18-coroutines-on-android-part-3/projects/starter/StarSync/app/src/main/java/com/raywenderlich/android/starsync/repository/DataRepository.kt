package com.raywenderlich.android.starsync.repository

import com.raywenderlich.android.starsync.contract.DataRepositoryContract
import com.raywenderlich.android.starsync.contract.ItemListCallback
import com.raywenderlich.android.starsync.repository.local.LocalRepo
import com.raywenderlich.android.starsync.repository.model.People
import com.raywenderlich.android.starsync.repository.remote.RemoteRepo

class DataRepository(val localRepo: LocalRepo, val remoteRepo: RemoteRepo) :
    DataRepositoryContract {


  override fun getDataFromLocal(): List<People> {
    return localRepo.getData()
  }

  override fun saveData(itemList: List<People>) {
    localRepo.saveData(itemList)
  }


  override fun getDataFromRemoteUsingCallback(itemListCallback: ItemListCallback) {
    remoteRepo.getDataUsingCallback(itemListCallback)
  }

  override suspend fun getDataFromRemoteUsingCoroutines(): List<People> {
    return remoteRepo.getDataUsingCoroutines()
  }
}
