import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun main() {
  val flowOfStrings = flow {
    emit("")

    for (number in 0..100) {
      emit("Emitting: $number")
    }
  }

  GlobalScope.launch {
    flowOfStrings.collect()
  }

//  GlobalScope.launch {
//    flowOfStrings
//        .map { it.split(" ") }
//        .map { it.last() }
//        .flowOn(Dispatchers.IO)
//        .delayEach(100)
//        .catch { }
//        .flowOn(Dispatchers.Default)
//        .collect { value ->
//          println(value)
//        }
//  }

  GlobalScope.launch {
    flowOfStrings
        .map { it.split(" ") }
        .map { it[1] }
        .catch {
          it.printStackTrace()
          // send the fallback values
          emit("Fallback")
        }
        .flowOn(Dispatchers.Default)
        .collect { println(it) }

    println("The code still works!")
  }

  Thread.sleep(2000)
}
