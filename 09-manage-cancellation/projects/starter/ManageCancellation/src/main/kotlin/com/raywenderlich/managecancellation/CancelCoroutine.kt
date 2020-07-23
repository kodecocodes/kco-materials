package com.raywenderlich.managecancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val job = launch {
    repeat(1000) { i ->
      println("$i. Crunching numbers [Beep.Boop.Beep]...")
      delay(500L)
    }
  }
  delay(1300L) // delay a bit
  println("main: I am tired of waiting!")
  job.cancel() // cancels the job
  job.join() // waits for job's completion
  println("main: Now I can quit.")
}