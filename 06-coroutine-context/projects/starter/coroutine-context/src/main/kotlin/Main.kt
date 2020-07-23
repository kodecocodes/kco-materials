import kotlinx.coroutines.*

fun main() {
  GlobalScope.launch {
    println("In a coroutine")
  }
  Thread.sleep(50)
}