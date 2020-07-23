package view

import contextProvider.CoroutineContextProviderImpl
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import presentation.MainPresenter

class MainViewTest {

  // 1
  private val testCoroutineDispatcher = TestCoroutineDispatcher()
  // 2
  private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
  // 3
  private val testCoroutineContextProvider = CoroutineContextProviderImpl(testCoroutineDispatcher)

  // 4
  private val mainPresenter by lazy { MainPresenter() }
  private val mainView by lazy {
    MainView(
        mainPresenter,
        testCoroutineContextProvider,
        testCoroutineScope
    )
  }


  @Test
  fun testFetchUserData() = testCoroutineScope.runBlockingTest {
    assertNull(mainView.userData)
    mainView.fetchUserData()

    advanceTimeBy(1000)

    assertEquals("Filip", mainView.userData?.name)
    mainView.printUserData()
  }
}