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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.disneyexplorer.R
import com.raywenderlich.android.disneyexplorer.databinding.ActivityIntroBinding
import com.raywenderlich.android.disneyexplorer.ui.compose.DisneyComposeActivity

class IntroActivity : AppCompatActivity() {
  private lateinit var binding: ActivityIntroBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Set theme explicitly because we used another theme for the splash screen
    setTheme(R.style.Theme_DisneyExplorer)
    binding = ActivityIntroBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setClickListeners()
  }

  private fun setClickListeners() = with(binding) {
    chapter14.setOnClickListener { BackgroundProcessingActivity.start(this@IntroActivity) }
    chapter15.setOnClickListener { UiLayerActivity.start(this@IntroActivity) }
    chapter16.setOnClickListener { DisneyActivity.start(this@IntroActivity) }
    compose.setOnClickListener { DisneyComposeActivity.start(this@IntroActivity) }
  }
}
