package com.raywenderlich.android.starsync.repository.remote

import com.raywenderlich.android.starsync.repository.model.BasePeople
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

  @GET(RemoteApi.PEOPLE_ROUTE)
  fun getUsingEnqueue(): Call<BasePeople>

  @GET(RemoteApi.PEOPLE_ROUTE)
  fun getUsingCoroutines(): Deferred<BasePeople>
}