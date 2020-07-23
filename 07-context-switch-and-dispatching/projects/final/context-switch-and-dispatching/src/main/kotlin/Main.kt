import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

fun main() {
  val executorDispatcher = Executors
      .newWorkStealingPool()
      .asCoroutineDispatcher()

  GlobalScope.launch(context = executorDispatcher) {
    println(Thread.currentThread().name)
  }

  Thread.sleep(50)
}