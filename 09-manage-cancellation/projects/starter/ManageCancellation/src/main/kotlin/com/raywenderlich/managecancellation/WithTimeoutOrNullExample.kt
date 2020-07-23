package com.raywenderlich.managecancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() = runBlocking {
  val result = withTimeoutOrNull(1300L) {
    repeat(1000) { i ->
      println("$i. Crunching numbers [Beep.Boop.Beep]...")
      delay(500L)
    }
    "Done" // will get canceled before it produces this result
  }
  // Result will be `null`
  println("Result is $result")
}
