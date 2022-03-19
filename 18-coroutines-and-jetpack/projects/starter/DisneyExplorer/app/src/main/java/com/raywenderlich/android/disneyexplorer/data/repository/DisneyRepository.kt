package com.raywenderlich.android.disneyexplorer.data.repository

import com.raywenderlich.android.disneyexplorer.data.model.DisneyCharacter
import kotlinx.coroutines.flow.Flow

interface DisneyRepository {

  fun getDisneyCharacters(): Flow<List<DisneyCharacter>>

  suspend fun getFreshData()
}
