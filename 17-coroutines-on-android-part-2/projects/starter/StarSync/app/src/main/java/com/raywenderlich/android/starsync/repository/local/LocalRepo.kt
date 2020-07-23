package com.raywenderlich.android.starsync.repository.local

import android.content.Context
import com.raywenderlich.android.starsync.contract.LocalRepositoryContract
import com.raywenderlich.android.starsync.repository.model.People

class LocalRepo(context: Context) : LocalRepositoryContract {

  private val localDatabase = PeopleDatabase.getInstance(context)?.peopleDao()

  @Throws(Exception::class)
  override fun saveData(itemList: List<People>) {
    // Iterate over the list items
    itemList.forEach {
      // Insert items in database
      localDatabase?.insert(it)
    }
  }

  override fun getData(): List<People> {
    return localDatabase?.getPeopleList() ?: emptyList()
  }
}