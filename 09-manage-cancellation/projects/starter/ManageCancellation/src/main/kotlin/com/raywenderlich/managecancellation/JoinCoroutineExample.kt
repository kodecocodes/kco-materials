package com.raywenderlich.managecancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val job = launch {
    println("Crunching numbers [Beep.Boop.Beep]...")
    delay(500L)
  }

  // waits for job's completion
  job.join()
  println("main: Now I can quit.")
}