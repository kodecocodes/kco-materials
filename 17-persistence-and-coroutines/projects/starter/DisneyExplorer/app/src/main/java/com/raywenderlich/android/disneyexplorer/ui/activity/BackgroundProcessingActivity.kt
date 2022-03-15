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
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.disneyexplorer.R
import com.raywenderlich.android.disneyexplorer.common.utils.showToast
import com.raywenderlich.android.disneyexplorer.data.model.DisneyCharacter
import com.raywenderlich.android.disneyexplorer.databinding.ActivityBackgroundProcessingBinding
import com.raywenderlich.android.disneyexplorer.di.DependencyHolder
import com.raywenderlich.android.disneyexplorer.ui.activity.ProcessingMethod.*
import com.raywenderlich.android.disneyexplorer.ui.adapter.list.DisneyAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

enum class ProcessingMethod {
  MAIN_THREAD, THREAD, HANDLER, HANDLER_THREAD, EXECUTOR, RX_JAVA, COROUTINES
}

class BackgroundProcessingActivity : ComponentActivity() {
  // Change this variable in order to change the processing method
  private var processingMethod: ProcessingMethod = COROUTINES

  private lateinit var binding: ActivityBackgroundProcessingBinding
  private var handlerThread: HandlerThread? = null
  private val getCharactersRunnable by lazy { GetCharactersRunnable() }
  private val adapter by lazy { DisneyAdapter() }
  private val disneyApiService by lazy { DependencyHolder.apiService }
  private var disposable: Disposable? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityBackgroundProcessingBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initUi()
    startLoadingAnimation()
    setClickListeners()
  }

  private fun initUi() = with(binding) {
    startLoadingAnimation()
    currentProcessingMethod.text = "Processing method: ${processingMethod.name}"
    characterList.adapter = adapter
    characterList.layoutManager = LinearLayoutManager(
      this@BackgroundProcessingActivity,
      RecyclerView.VERTICAL, false
    )
  }

  private fun startLoadingAnimation() {
    val animation = AnimationUtils.loadAnimation(this, R.anim.rotation)
    binding.loadingAnimation.startAnimation(animation)
  }

  private fun setClickListeners() {
    binding.startProcessing.setOnClickListener {
      startProcessing()
    }
  }

  private fun startProcessing() {
    when (processingMethod) {
      MAIN_THREAD -> runUiBlockingProcessing()
      THREAD -> runProcessingWithThread()
      HANDLER -> runProcessingWithHandler()
      HANDLER_THREAD -> runProcessingWithHandlerThread()
      EXECUTOR -> runProcessingWithExecutor()
      RX_JAVA -> runProcessingWithRxJava()
      COROUTINES -> runProcessingWithCoroutines()
    }
  }

  private fun runUiBlockingProcessing() {
    // This will block the thread while executing
    showToast("Result: ${fibonacci(40)}")
  }

  private fun runProcessingWithThread() {
    // Create a new Thread which will do the work
    Thread(getCharactersRunnable).start()
  }

  private fun runProcessingWithHandler() {
    // Create a Handler associated with the main thread
    val handler = Handler(Looper.getMainLooper())
    // Create a new thread and give it some work to do
    Thread {
      val characters = disneyApiService.getDisneyCharacters()
      // Use the handler to show results on the main thread
      handler.post {
        showResults(characters)
      }
    }.start()
  }

  private fun runProcessingWithHandlerThread() {
    // Create a new thread
    handlerThread = HandlerThread("MyHandlerThread")

    handlerThread?.let {
      handlerThread?.start()
      // Create a new Handler that will use HandlerThread's Looper to do its work
      val handler = Handler(it.looper)
      // Create a Handler with the main thread Looper
      val mainHandler = Handler(Looper.getMainLooper())
      // This will run on the HandlerThread created above
      handler.post {
        val characters = disneyApiService.getDisneyCharacters()
        // Use the handler associated with the main thread to show the results
        mainHandler.post {
          showResults(characters)
        }
      }
    }
  }

  private fun runProcessingWithExecutor() {
    // Create a new Executor with a fixed thread pool and execute the Runnable
    val executor = Executors.newFixedThreadPool(1)
    executor.execute(getCharactersRunnable)
  }

  private fun runProcessingWithRxJava() {
    // 1
    disposable = Single.create<List<DisneyCharacter>> { emitter ->
      // 2
      val characters = disneyApiService.getDisneyCharacters()
      emitter.onSuccess(characters)
      // 3
    }.subscribeOn(Schedulers.io())
      // 4
      .observeOn(AndroidSchedulers.mainThread())
      // 5
      .subscribe(::showResults, Throwable::printStackTrace)
  }

  private fun runProcessingWithCoroutines() {
    // Create a new coroutine in the scope tied to the lifecycle of the Activity
    lifecycleScope.launch(Dispatchers.IO) {
      // Make the network request on a background thread
      val characters = disneyApiService.getDisneyCharacters()
      // Switch to the main thread to show the results
      withContext(Dispatchers.Main) {
        showResults(characters)
      }
    }
  }

  private fun showResults(characters: List<DisneyCharacter>) = with(binding) {
    characterList.visibility = View.VISIBLE
    uiProcessingContainer.visibility = View.GONE
    adapter.setData(characters)
  }

  private fun fibonacci(number: Int): Long {
    return if (number == 1 || number == 2) {
      1
    } else {
      fibonacci(number - 1) + fibonacci(number - 2)
    }
  }

  inner class GetCharactersRunnable : Runnable {
    override fun run() {
      val characters = disneyApiService.getDisneyCharacters()
      runOnUiThread { showResults(characters) }
    }
  }

  override fun onDestroy() {
    // Dispose the disposable if it has been created
    disposable?.dispose()

    // Stop the HandlerThread because it's no longer needed
    handlerThread?.quit()
    super.onDestroy()
  }

  companion object {
    fun start(from: Context) =
      from.startActivity(Intent(from, BackgroundProcessingActivity::class.java))
  }
}
