import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

fun main() {
  val flowOfStrings = flow {
    emit("")

    for (number in 0..100) {
      emit("Emitting: $number")
    }
  }

  GlobalScope.launch {
    flowOfStrings
      .map { it.split(" ") }
      .map { it[1] }
      .catch {
        it.printStackTrace()
        // send the fallback value or values
        emit("Fallback")
      }
      .flowOn(Dispatchers.Default)
      .collect { println(it) }

    println("The code still works!")
  }

  Thread.sleep(1000)
}


