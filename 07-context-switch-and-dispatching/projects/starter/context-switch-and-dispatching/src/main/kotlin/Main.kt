import kotlinx.coroutines.*

fun main() {
  GlobalScope.launch {
    println("This is a coroutine")
  }
  Thread.sleep(50)
}