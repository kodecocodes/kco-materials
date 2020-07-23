/*
 * Copyright (c) 2018 Razeware LLC
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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.starsync.ui.mainscreen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.raywenderlich.android.starsync.BuildConfig
import com.raywenderlich.android.starsync.R
import com.raywenderlich.android.starsync.contract.PresenterContract
import com.raywenderlich.android.starsync.contract.ViewContract
import com.raywenderlich.android.starsync.repository.DataRepository
import com.raywenderlich.android.starsync.repository.local.LocalRepo
import com.raywenderlich.android.starsync.repository.model.People
import com.raywenderlich.android.starsync.repository.remote.RemoteRepo
import com.raywenderlich.android.starsync.utils.hide
import com.raywenderlich.android.starsync.utils.show
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), ViewContract {

  private var peopleListAdapter = PeopleListAdapter()

  private var presenter: PresenterContract? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Setup recyclerView
    recyclerView.apply {
      hasFixedSize()
      layoutManager = LinearLayoutManager(this.context)
      addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
      adapter = peopleListAdapter
    }

    // Setup the repository
    val localRepo = LocalRepo(applicationContext)
    val remoteRepo = RemoteRepo(applicationContext)
    val repository = DataRepository(localRepo, remoteRepo)

    // Setup the presenter
    presenter = MainActivityPresenter(this, repository)
    //TODO: Observe the lifecycle

    // Setup FAB
    fab.setOnClickListener {
      // 1
      presenter?.getData()

      // 2
      // demonstrateUsingMainDispatcher()
    }
  }

  private fun demonstrateUsingMainDispatcher() {
    GlobalScope.launch(Dispatchers.Main) {

      // Runs on Main Thread
      var stringToShow = "Luke"
      prompt(stringToShow)

      // Runs on Background Thread
      withContext(Dispatchers.IO) {
        delay(5000)
        stringToShow = "Darth Vader"
      }

      // Runs on Main Thread
      prompt(stringToShow)
    }
  }

  override fun updateWithData(itemList: List<People>) {
    peopleListAdapter.loadItemList(itemList)
  }

  override fun showLoading() {
    recyclerView.hide()
    loadingProgress.show()
  }

  override fun hideLoading() {
    recyclerView.show()
    loadingProgress.hide()
  }

  override fun prompt(string: String?) {
    // Log to logcat
    if (BuildConfig.DEBUG) {
      Log.d(javaClass.simpleName, string ?: "-")
    }

    // Show a snackbar message
    Snackbar.make(rootLayout, string ?: "-", Snackbar.LENGTH_SHORT).show()
  }

  override fun onResume() {
    super.onResume()
    presenter?.getData()
  }

  override fun onDestroy() {
    presenter?.cleanup()

    super.onDestroy()
  }
}