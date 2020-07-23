package com.raywenderlich.managecancellation

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() = runBlocking {
  try {
    withTimeout(1500L) {
      repeat(1000) { i ->
        println("$i. Crunching numbers [Beep.Boop.Beep]...")
        delay(500L)
      }
    }
  } catch (e: TimeoutCancellationException) {
    println("Caught ${e.javaClass.simpleName}")
  }
}