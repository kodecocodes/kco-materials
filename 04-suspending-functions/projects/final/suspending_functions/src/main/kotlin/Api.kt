import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

suspend fun <T : Any> getValue(provider: () -> T): T =
    suspendCoroutine { continuation ->
      continuation.resumeWith(Result.runCatching { provider() })
    }

fun executeBackground(action: suspend () -> Unit) {
  GlobalScope.launch { action() }
}

fun executeMain(action: suspend () -> Unit) {
  GlobalScope.launch(context = Dispatchers.Main) { action() }
}