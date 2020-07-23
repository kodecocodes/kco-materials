package com.raywenderlich.android.starsync.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raywenderlich.android.starsync.repository.model.People

@Database(entities = [People::class], version = 1, exportSchema = false)
abstract class PeopleDatabase : RoomDatabase() {

  abstract fun peopleDao(): PeopleDao

  companion object {
    private var INSTANCE: PeopleDatabase? = null

    fun getInstance(context: Context): PeopleDatabase? {
      if (INSTANCE == null) {
        synchronized(PeopleDatabase::class) {
          INSTANCE = Room.databaseBuilder(context.applicationContext,
              PeopleDatabase::class.java, "people.db")
              .build()
        }
      }
      return INSTANCE
    }

    fun destroyInstance() {
      INSTANCE = null
    }
  }
}