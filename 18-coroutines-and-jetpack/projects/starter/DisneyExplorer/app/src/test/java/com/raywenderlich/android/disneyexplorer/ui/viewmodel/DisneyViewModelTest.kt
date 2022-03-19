package com.raywenderlich.android.disneyexplorer.ui.viewmodel

import com.raywenderlich.android.disneyexplorer.data.repository.DisneyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.yield
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class DisneyViewModelTest {
  private val disneyRepoMock: DisneyRepository = mock()
  private lateinit var viewModel: DisneyViewModel

  @ExperimentalCoroutinesApi
  @Before
  fun setUp() {
    viewModel = DisneyViewModel(disneyRepoMock)
  }

  @ExperimentalCoroutinesApi
  @After
  fun tearDown() {
  }

  @Test
  fun `test getFreshData calls repository to get data`() = runTest {
    viewModel.getFreshData()
    yield()

    verify(disneyRepoMock).getFreshData()
  }
}
