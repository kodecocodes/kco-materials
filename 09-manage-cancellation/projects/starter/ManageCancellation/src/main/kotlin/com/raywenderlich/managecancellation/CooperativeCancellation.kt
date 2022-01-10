package com.raywenderlich.managecancellation

import kotlinx.coroutines.*

fun main() = runBlocking {
  val startTime = System.currentTimeMillis()
  val job = launch(Dispatchers.Default) {
    var nextPrintTime = startTime
    var i = 0
    while (i < 5 && isActive) {
      if (System.currentTimeMillis() >= nextPrintTime) {
        println("Doing heavy work: $i")
        i++
        nextPrintTime += 500L
      }
    }
  }
  delay(1000)
  println("Cancelling coroutine")
  job.cancel()
  println("Main: now I can quit!")
}