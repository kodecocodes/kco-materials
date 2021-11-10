package view

import contextProvider.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.User
import presentation.MainPresenter

class MainView(
    private val presenter: MainPresenter,
    private val contextProvider: CoroutineContextProvider,
    private val coroutineScope: CoroutineScope
) {

  var userData: User? = null

  fun fetchUserData() {
    coroutineScope.launch(contextProvider.context()) {
      userData = presenter.getUser("101")
    }
  }

  fun printUserData() {
    println(userData)
  }
}