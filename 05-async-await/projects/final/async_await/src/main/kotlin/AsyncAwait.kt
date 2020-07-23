import kotlinx.coroutines.*
import java.io.File

fun main() {
  val scope = CustomScope()

  scope.launch {
    println("Launching in custom scope")
  }

  scope.onStop() //cancels all the coroutines
}

fun multipleAwaitExample() {
  val userId = 992 // the ID of the user we want

  val job = GlobalScope.launch {
    val userDeferred = getUserByIdFromNetwork(userId, this)
    val usersFromFileDeferred = readUsersFromFile("users.txt", this)

    if (isActive) {
      println("Finding user")
      val userStoredInFile = checkUserExists(
          userDeferred.await(),
          usersFromFileDeferred.await()
      )

      if (userStoredInFile) {
        println("Found user in file!")
      }
    }
  }

  //  job.cancel() // uncomment if you want to cancel all of the functions
  Thread.sleep(5000)
}

private fun checkUserExists(user: User, users: List<User>): Boolean {
  return user in users
}

private fun getUserByIdFromNetwork(
    userId: Int,
    scope: CoroutineScope) =
    scope.async {
      if (!scope.isActive) {
        return@async User(0, "", "")
      }
      println("Retrieving user from network")
      delay(3000)
      println("Still in the coroutine")

      User(userId, "Filip", "Babic") // we simulate the network call
    }

private fun readUsersFromFile(
    filePath: String,
    scope: CoroutineScope) =
    scope.async {
      println("Reading the file of users")
      delay(1000)

      if (!scope.isActive) {
        return@async emptyList<User>()
      }

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

data class User(val id: Int, val name: String, val lastName: String)

