import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class CustomScope : CoroutineScope {

  private var parentJob = Job()

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + parentJob

  fun onStart() {
    parentJob = Job()
  }

  fun onStop() {
    parentJob.cancel()
    // You can also cancel the whole scope
    // with `cancel(cause: CancellationException)`
  }
}