package view

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import model.User
import presentation.MainPresenter

class MainView(
    private val presenter: MainPresenter
) {

  var userData: User? = null

  fun fetchUserData() {
    GlobalScope.launch(Dispatchers.IO) {
      userData = presenter.getUser("101")
    }
  }

  fun printUserData() {
    println(userData)
  }
}