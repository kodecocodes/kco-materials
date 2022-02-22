import kotlinx.coroutines.*

fun main() {
  GlobalScope.launch(Dispatchers.Main) {
    val user = getUserSuspend("101")

    println(user)
  }
}

suspend fun getUserSuspend(userId: String): User = withContext(Dispatchers.Default) {
  delay(1000)

  User(userId, "Filip")
}