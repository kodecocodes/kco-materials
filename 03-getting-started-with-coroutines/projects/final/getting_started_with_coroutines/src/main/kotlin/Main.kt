import kotlinx.coroutines.*

fun main() {
  GlobalScope.launch {
    val bgThreadName = Thread.currentThread().name
    println("I’m Job 1 in thread $bgThreadName")
    delay(200)
    GlobalScope.launch(Dispatchers.Main) {
      val uiThreadName = Thread.currentThread().name
      println("I’m Job 2 in thread $uiThreadName")
    }
  }
  Thread.sleep(1000)
}