package com.raywenderlich.android.starsync.repository

import com.raywenderlich.android.starsync.repository.model.People

class MockData {
  fun generateFakeData(): List<People> {
    val itemList = ArrayList<People>(3)

    itemList.apply {
      add(People("Luke Skywalker", "172", "77", "blond", "fair", "blue", "male"))

      add(People("Darth Vader", "202", "136", "none", "white", "yellow", "male"))

      add(People("Leia Organa", "150", "49", "brown", "light", "brown", "female"))
    }

    return itemList
  }
}