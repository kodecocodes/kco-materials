fun main() {
  val userId = 992

  getUserByIdFromNetwork(userId) { user ->
    println(user)
  }
}

private fun getUserByIdFromNetwork(userId: Int, onUserReady: (User) -> Unit) {
  Thread.sleep(3000)

  onUserReady(User(userId, "Filip", "Babic"))
}

data class User(val id: Int, val name: String, val lastName: String)