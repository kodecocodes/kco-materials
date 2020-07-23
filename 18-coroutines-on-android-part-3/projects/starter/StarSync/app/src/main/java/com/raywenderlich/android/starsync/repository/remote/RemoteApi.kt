package com.raywenderlich.android.starsync.repository.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RemoteApi {

  private const val BASE_URL = "https://swapi.co/api/"
  const val PEOPLE_ROUTE = "people"

  val starWarsService: RetrofitService = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .build().create(RetrofitService::class.java)
}