import kotlinx.coroutines.*
import java.io.File

fun main() {
  val scope = CustomScope()

  scope.launch {
    println("Launching in custom scope")
  }

  scope.onStop() //cancels all the coroutines
}

private suspend fun getUserByIdFromNetwork(userId: Int) = GlobalScope.async {
  println("Retrieving user from network")
  delay(3000)
  println("Still in the coroutine")

  User(userId, "Filip", "Babic") // we simulate the network call
}

private fun readUsersFromFile(filePath: String) =
  GlobalScope.async {
    println("Reading the file of users")
    delay(1000)

    File(filePath)
      .readLines()
      .asSequence()
      .filter { it.isNotEmpty() }
      .map {
        val data = it.split(" ") // [id, name, lastName]

        if (data.size == 3) data else emptyList()
      }
      .filter {
        it.isNotEmpty()
      }
      .map {
        val userId = it[0].toInt()
        val name = it[1]
        val lastName = it[2]

        User(userId, name, lastName)
      }
      .toList()
  }

private fun checkUserExists(user: User, users: List<User>): Boolean {
  return user in users
}

data class User(val id: Int, val name: String, val lastName: String)