import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun main() {

  GlobalScope.launch {
    val user = getUserSuspend("101")

    println(user)
  }
}

suspend fun getUserSuspend(userId: String): User {
  delay(1000)

  return User(userId, "Filip")
}

suspend fun readFileSuspend(path: String): File =
    suspendCoroutine {
      readFile(path) { file ->
        it.resume(file)
      }
    }

fun readFile(path: String, onReady: (File) -> Unit) {
  Thread.sleep(1000)
  // some heavy operation

  onReady(File(path))
}