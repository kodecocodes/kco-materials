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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.disneyexplorer.common.utils.FlowUtils
import com.raywenderlich.android.disneyexplorer.databinding.ActivityUiLayerBinding
import com.raywenderlich.android.disneyexplorer.ui.adapter.NumbersAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class UiLayerActivity : AppCompatActivity() {
  private lateinit var binding: ActivityUiLayerBinding
  private val adapter by lazy { NumbersAdapter() }
  private val mainScope by lazy { MainScope() }
  // TODO: Declare flowCollectorJob here

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityUiLayerBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setupList()
    runProcessingWithFlow()
  }

  private fun setupList() = with(binding) {
    numbers.layoutManager = LinearLayoutManager(this@UiLayerActivity, RecyclerView.VERTICAL, false)
    numbers.setHasFixedSize(true)
    numbers.adapter = adapter
  }

  private fun runProcessingWithFlow() {
    // TODO: Add implementation here
  }

  override fun onStart() {
    super.onStart()
    println("FLOW: DisneyActivity.onStart")
  }

  override fun onStop() {
    // TODO: Cancel flowCollectorJob here
    super.onStop()
    println("FLOW: DisneyActivity.onStop")
  }

  override fun onDestroy() {
    mainScope.cancel()
    super.onDestroy()
  }

  companion object {
    fun start(from: Context) =
      from.startActivity(Intent(from, UiLayerActivity::class.java))
  }
}