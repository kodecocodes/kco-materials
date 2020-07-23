import contextProvider.CoroutineContextProviderImpl
import kotlinx.coroutines.*

fun main() {
  val backgroundContextProvider = CoroutineContextProviderImpl(Dispatchers.Default)

  val coroutineErrorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    throwable.printStackTrace() // we just print the error here
  }

  val emptyParentJob = Job()

  val combinedContext = backgroundContextProvider.context() + coroutineErrorHandler + emptyParentJob

  GlobalScope.launch(context = combinedContext) {
    println(Thread.currentThread().name)
  }

  Thread.sleep(50)
}