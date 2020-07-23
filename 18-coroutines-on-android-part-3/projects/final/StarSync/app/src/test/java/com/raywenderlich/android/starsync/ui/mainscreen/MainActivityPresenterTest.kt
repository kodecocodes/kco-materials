package com.raywenderlich.android.starsync.ui.mainscreen

import com.raywenderlich.android.starsync.contract.DataRepositoryContract
import com.raywenderlich.android.starsync.contract.ViewContract
import com.raywenderlich.android.starsync.repository.MockData
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Test


class MainActivityPresenterTest {

  lateinit var repository: DataRepositoryContract
  lateinit var presenter: MainActivityPresenter
  lateinit var view: ViewContract

  val mockData = MockData()
  val mockedItemList = mockData.generateFakeData()

  @Before
  fun setUp() {
    repository = mockk(relaxUnitFun = true)
    view = mockk(relaxUnitFun = true)

    // Given
    presenter = MainActivityPresenter(view, repository, Dispatchers.Unconfined,
        Dispatchers.Unconfined)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun presenter_getData() {

    // Given
    every { repository.getDataFromLocal() } returns mockedItemList
    coEvery { repository.getDataFromRemoteUsingCoroutines() } returns mockedItemList

    // When
    presenter.getData()

    // Then
    verifyOrder {
      view.hideLoading()
      view.showLoading()
    }

    coVerify {
      repository.getDataFromLocal()
    }

    verify {
      view.prompt(any())
    }

    coVerify {
      repository.saveData(mockedItemList)
    }

    verifyOrder {
      view.hideLoading()
      view.updateWithData(mockedItemList)
    }

    coVerify {
      repository.getDataFromRemoteUsingCoroutines()
    }

    verify {
      view.prompt(any())
    }

    coVerify {
      repository.saveData(mockedItemList)
    }

    verifyOrder {
      view.hideLoading()
      view.updateWithData(mockedItemList)
    }
  }

  @Test
  fun presenter_updateData() {

    // When
    presenter.updateData(mockedItemList, "Repo")

    // Then
    verify {
      view.prompt(any())
    }

    coVerify {
      repository.saveData(mockedItemList)
    }

    verifyOrder {
      view.hideLoading()
      view.updateWithData(mockedItemList)
    }
  }
}