package presentation

import kotlinx.coroutines.delay
import model.User

class MainPresenter {

  suspend fun getUser(userId: String): User {
    delay(1000)

    return User(userId, "Filip")
  }
}