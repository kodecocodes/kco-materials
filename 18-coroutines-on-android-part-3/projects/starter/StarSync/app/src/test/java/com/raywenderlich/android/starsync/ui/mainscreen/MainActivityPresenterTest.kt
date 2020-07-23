package com.raywenderlich.android.starsync.ui.mainscreen

import com.raywenderlich.android.starsync.contract.DataRepositoryContract
import com.raywenderlich.android.starsync.contract.ViewContract
import com.raywenderlich.android.starsync.repository.MockData
import org.junit.After
import org.junit.Before
import org.junit.Test


class MainActivityPresenterTest {

  lateinit var repository: DataRepositoryContract
  lateinit var presenter: MainActivityPresenter
  lateinit var view: ViewContract

  val mockData = MockData()
  val mockedItemList= mockData.generateFakeData()

  @Before
  fun setUp() {
    // TODO: Add setup() implementation here
  }

  @After
  fun tearDown() {
    // TODO: Add tearDown() implementation here
  }

  @Test
  fun presenter_getData() {
    // TODO: Add presenter_getData() implementation here
  }

  @Test
  fun presenter_updateData() {
    // TODO: Add presenter_updateData() implementation here
  }
}