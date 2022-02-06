import contextProvider.CoroutineContextProvider
import contextProvider.CoroutineContextProviderImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
  val parentJob = Job()
  val provider: CoroutineContextProvider = CoroutineContextProviderImpl(
    context = parentJob + Dispatchers.IO
  )

  GlobalScope.launch(context = provider.context()) {
    println(Thread.currentThread().name)
  }

  Thread.sleep(50)
}