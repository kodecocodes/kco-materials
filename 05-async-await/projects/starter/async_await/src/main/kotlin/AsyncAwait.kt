import kotlinx.coroutines.*
import java.io.File

fun main() {
  val launch = GlobalScope.launch {
    val dataDeferred = getUserByIdFromNetwork(1312)
    println("Not cancelled")
    // do something with the data

    val data = dataDeferred.await()
    println(data)
  }

  Thread.sleep(50)
  launch.cancel()

  while (true) { // stops the program from finishing
  }
}

private suspend fun getUserByIdFromNetwork(
  userId: Int,
  parentScope: CoroutineScope) =
  parentScope.async {
    if (!isActive) {
      return@async User(0, "", "")
    }
    println("Retrieving user from network")
    delay(3000)
    println("Still in the coroutine")

    User(userId, "Filip", "Babic") // we simulate the network call
}

data class User(val id: Int, val name: String, val lastName: String)

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