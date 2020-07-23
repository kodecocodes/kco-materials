package com.raywenderlich.android.starsync.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.raywenderlich.android.starsync.repository.model.People

@Dao
interface PeopleDao {

  @Query("SELECT * from people")
  fun getPeopleList(): List<People>?

  @Insert(onConflict = REPLACE)
  fun insert(item: People)
}