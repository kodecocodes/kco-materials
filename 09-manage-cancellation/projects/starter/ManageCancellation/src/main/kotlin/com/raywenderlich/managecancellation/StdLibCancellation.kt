package com.raywenderlich.managecancellation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val job = launch(Dispatchers.Default) {
    var i = 0
    while (i < 1000) {
      println("Doing heavy work ${i++}")
      delay(500)
    }
  }
  delay(1200)
  println("Cancelling")
  job.cancel()
  println("Main: Now I can quit!")
}