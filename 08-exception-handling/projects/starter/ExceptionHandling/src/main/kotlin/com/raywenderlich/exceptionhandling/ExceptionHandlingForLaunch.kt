import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
  runBlocking {
    val job = GlobalScope.launch {
      println("1. Exception created via launch coroutine")

      // Will NOT be handled by
      // Thread.defaultUncaughtExceptionHandler
      // since it is being handled later by `invokeOnCompletion`
      throw IndexOutOfBoundsException()
    }

    // Handle the exception thrown from `launch` coroutine builder
    job.invokeOnCompletion { exception ->
      println("2. Caught $exception")
    }

    // This suspends coroutine until this job is complete.
    job.join()
  }
}
