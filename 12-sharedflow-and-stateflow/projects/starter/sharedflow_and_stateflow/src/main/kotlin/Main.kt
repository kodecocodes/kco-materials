import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
  (1..10000).forEach {
    GlobalScope.launch {
      val threadName = Thread.currentThread().name
      println("$it printed on thread $threadName")
    }
  }
  Thread.sleep(1000)
}