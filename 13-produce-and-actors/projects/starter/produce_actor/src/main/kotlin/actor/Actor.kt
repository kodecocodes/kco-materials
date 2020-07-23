package actor

import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlin.random.Random

// 1
object completionHandler : CompletionHandler {
  override fun invoke(cause: Throwable?) {
    println("Completed!")
  }
}

fun main() {
  // 2
  val actor = GlobalScope.actor<String>(
      onCompletion = completionHandler,
      capacity = 10) {
    // 3
    for (data in channel) {
      println(data)
    }
  }

  // 4
  (1..10).forEach {
    actor.offer(Random.nextInt(0, 20).toString())
  }
  // 5
  actor.close()
  // 6
  Thread.sleep(500L)
}

