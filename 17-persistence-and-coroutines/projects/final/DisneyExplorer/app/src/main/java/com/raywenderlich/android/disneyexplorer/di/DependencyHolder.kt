/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.disneyexplorer.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.raywenderlich.android.disneyexplorer.App
import com.raywenderlich.android.disneyexplorer.BuildConfig
import com.raywenderlich.android.disneyexplorer.common.utils.CoroutineContextProvider
import com.raywenderlich.android.disneyexplorer.data.database.CharacterDao
import com.raywenderlich.android.disneyexplorer.data.database.DisneyDatabase
import com.raywenderlich.android.disneyexplorer.data.networking.DisneyApi
import com.raywenderlich.android.disneyexplorer.data.networking.DisneyApiService
import com.raywenderlich.android.disneyexplorer.data.repository.DisneyRepository
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit

private const val BASE_URL = "http://api.disneyapi.dev/"
private const val DB_NAME = "CharactersDatabase"

/* This is a very basic and naive approach to implement Dependency Injection. But, it will suffice
 for this sample project.
 */
object DependencyHolder {
  val disneyApi: DisneyApi by lazy { retrofit.create(DisneyApi::class.java) }
  val characterDao: CharacterDao by lazy { getDatabase().characterDao() }
  val disneyRepository: DisneyRepository by lazy {
    DisneyRepository(
      apiService,
      characterDao,
    )
  }
  val apiService: DisneyApiService by lazy { DisneyApiService(disneyApi) }
  private val json = Json { ignoreUnknownKeys = true }
  private val contentType = "application/json".toMediaType()
  private val retrofit by lazy { buildRetrofit() }

  private fun buildRetrofit() = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(getOkHttpClient())
    .addConverterFactory(json.asConverterFactory(contentType))
    .build()

  private fun getCoroutineContextProvider() = CoroutineContextProvider()

  private fun getDatabase() = Room.databaseBuilder(
    App.appContext, DisneyDatabase::class.java,
    DB_NAME
  ).build()

  private fun getOkHttpClient() = OkHttpClient.Builder()
    .apply { if (BuildConfig.DEBUG) eventListenerFactory(LoggingEventListener.Factory()) }
    .addInterceptor(getLoggingInterceptor())
    .build()

  private fun getLoggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
  }
}
