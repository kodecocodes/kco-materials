package com.raywenderlich.android.disneyexplorer.ui.compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.raywenderlich.android.disneyexplorer.data.model.DisneyCharacter
import com.raywenderlich.android.disneyexplorer.di.DependencyHolder
import com.raywenderlich.android.disneyexplorer.ui.theme.DisneyExplorerTheme
import com.raywenderlich.android.disneyexplorer.ui.viewmodel.DisneyViewModel
import com.raywenderlich.android.disneyexplorer.ui.viewmodel.DisneyViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DisneyComposeActivity : ComponentActivity() {
  private val viewModel: DisneyViewModel by viewModels {
    DisneyViewModelFactory(DependencyHolder.disneyRepository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      DisneyExplorerTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          MainDisneyScreen(viewModel = viewModel) { showToast() }
        }
      }
    }
  }

  private suspend fun showToast() {
    delay(200)
    Toast.makeText(this, "Refreshing Data", Toast.LENGTH_SHORT).show()
  }

  companion object {
    fun start(from: Context) =
      from.startActivity(Intent(from, DisneyComposeActivity::class.java))
  }
}

@Composable
fun MainDisneyScreen(
  viewModel: DisneyViewModel,
  onScreenLoaded: suspend () -> Unit
) {
  val lifecycleOwner = LocalLifecycleOwner.current
  val mainScreenScope = rememberCoroutineScope()
  Column {
    Toolbar {
      viewModel.getFreshData()
      mainScreenScope.launch {
        onScreenLoaded()
      }
    }
    val uiStateFlow = remember(viewModel.charactersFlow, lifecycleOwner) {
      viewModel.charactersFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val charactersList by uiStateFlow.collectAsState(emptyList())
    CharacterList(characterList = charactersList)
  }
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
