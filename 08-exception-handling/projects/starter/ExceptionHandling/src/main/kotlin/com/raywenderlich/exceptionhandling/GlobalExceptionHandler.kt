import kotlinx.coroutines.*

fun main() {
  runBlocking {
    // 1
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
      println("Caught $exception")
    }
    // 2
    val job = GlobalScope.launch(exceptionHandler) {
      throw AssertionError("My Custom Assertion Error!")
    }
    // 3
    val deferred = GlobalScope.async(exceptionHandler) {
      // Nothing will be printed,
      // relying on user to call deferred.await()
      throw ArithmeticException()
    }
    // 4
    // This suspends current coroutine until all given jobs are complete.
    joinAll(job, deferred)
  }
}