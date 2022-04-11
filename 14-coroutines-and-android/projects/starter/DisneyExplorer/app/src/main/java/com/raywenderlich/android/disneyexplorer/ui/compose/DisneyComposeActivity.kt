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

package com.raywenderlich.android.disneyexplorer.ui.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.raywenderlich.android.disneyexplorer.data.model.DisneyCharacter
import com.raywenderlich.android.disneyexplorer.ui.viewmodel.DisneyViewModel
import com.raywenderlich.android.disneyexplorer.ui.theme.DisneyExplorerTheme

class DisneyComposeActivity : ComponentActivity() {
  private val viewModel: DisneyViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      DisneyExplorerTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          MainDisneyScreen(viewModel = viewModel, lifecycle = lifecycle)
        }
      }
    }
  }

  companion object {
    fun start(from: Context) =
      from.startActivity(Intent(from, DisneyComposeActivity::class.java))
  }
}

@Composable
fun MainDisneyScreen(viewModel: DisneyViewModel, lifecycle: Lifecycle) {
  // remember the ui state until it changes
  val uiStateFlow = remember(viewModel.uiState) {
    // Automatically starts/stops collecting based on the specified lifecycle
    viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
  }
  // Collects the flow as State (Compose State)
  val charactersList by uiStateFlow.collectAsState(emptyList())
  CharacterList(characterList = charactersList)
}

@Composable
private fun CharacterList(characterList: List<DisneyCharacter>) {
  LazyColumn(contentPadding = PaddingValues(16.dp)) {
    items(characterList) { character ->
      DisneyCharacterCard(
        image = character.imageUrl,
        name = character.name
      )
    }
  }
}
