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

package com.raywenderlich.android.disneyexplorer.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.disneyexplorer.R
import com.raywenderlich.android.disneyexplorer.data.model.DisneyCharacter
import com.raywenderlich.android.disneyexplorer.databinding.ActivityNetworkingBinding
import com.raywenderlich.android.disneyexplorer.di.DependencyHolder
import com.raywenderlich.android.disneyexplorer.ui.adapter.list.DisneyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisneyActivity : AppCompatActivity() {
  private lateinit var binding: ActivityNetworkingBinding
  private val apiService by lazy { DependencyHolder.apiService }
  private val adapter by lazy { DisneyAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityNetworkingBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initUi()
    binding.startProcessing.setOnClickListener { fetchDisneyCharacters() }
  }

  private fun initUi() {
    startLoadingAnimation()
    binding.characterList.adapter = adapter
    binding.characterList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
  }

  private fun startLoadingAnimation() {
    val animation = AnimationUtils.loadAnimation(this, R.anim.rotation)
    binding.loadingAnimation.startAnimation(animation)
  }

  private fun showResults(characters: List<DisneyCharacter>) = with(binding) {
    characterList.visibility = View.VISIBLE
    uiProcessingContainer.visibility = View.GONE
    adapter.setData(characters)
  }

  private fun fetchDisneyCharacters() {
    // Create a new coroutine in the scope tied to the lifecycle of the Activity
    lifecycleScope.launch(Dispatchers.IO) {
      // Make the network request on a background thread
      runCatching { apiService.getDisneyCharacters() }
        .onSuccess {
          // Switch to the main thread to show the results
          withContext(Dispatchers.Main) {
            showResults(it)
          }
        }
        .onFailure { it.printStackTrace() }
    }
  }

  companion object {
    fun start(from: Context) =
      from.startActivity(Intent(from, DisneyActivity::class.java))
  }
}
