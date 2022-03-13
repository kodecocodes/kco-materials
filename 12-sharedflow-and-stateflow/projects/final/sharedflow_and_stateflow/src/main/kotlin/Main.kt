import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() {
  val coroutineScope = CoroutineScope(Dispatchers.Default)

  val stateFlow = MutableStateFlow("Author: Filip")

  println(stateFlow.value)

  coroutineScope.launch {
    stateFlow.collect {
      println(it)
    }
  }

  stateFlow.value = "Author: Luka"

  stateFlow.tryEmit("FPE: Max")

  coroutineScope.launch {
    stateFlow.emit("TE: Godfred")
  }

  Thread.sleep(50)
  coroutineScope.cancel()

  while (coroutineScope.isActive) {

  }
}