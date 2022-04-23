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

package com.raywenderlich.android.disneyexplorer.data.networking

import com.raywenderlich.android.disneyexplorer.data.model.CharactersResponse
import com.raywenderlich.android.disneyexplorer.data.model.DisneyCharacter
import com.raywenderlich.android.disneyexplorer.di.DependencyHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisneyApiService(private val disneyApi: DisneyApi) {

  fun getCharacters(
    onError: (Throwable) -> Unit,
    onSuccess: (List<DisneyCharacter>) -> Unit
  ) {
    // Make an asynchronous request
    disneyApi.getCharacters().enqueue(object : Callback<CharactersResponse> {
      override fun onResponse(
        call: Call<CharactersResponse>,
        response: Response<CharactersResponse>
      ) {
        // Invoke onSuccess lambda when the results are ready
        val data = response.body()
        if (data == null) {
          onError(Throwable("No response"))
        } else {
          onSuccess(data.data)
        }
      }

      override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
        // Invoke onError if an error happens
        onError(t)
      }
    })
  }

  fun getDisneyCharacters(): List<DisneyCharacter> {

    // Initiate the request
    val response = DependencyHolder.disneyApi.getCharacters().execute()

    // Process the response
    return response.body()?.data ?: emptyList()
  }
}

