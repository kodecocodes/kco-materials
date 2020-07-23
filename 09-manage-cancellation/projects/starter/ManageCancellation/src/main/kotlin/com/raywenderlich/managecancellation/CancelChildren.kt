package com.raywenderlich.managecancellation

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val parentJob = launch {
    val childOne = launch {
      repeat(1000) { i ->
        println("Child Coroutine 1: " +
            "$i. Crunching numbers [Beep.Boop.Beep]...")
        delay(500L)
      }
    }


    // Handle the exception thrown from `launch`
    // coroutine builder
    childOne.invokeOnCompletion { exception ->
      println("Child One: ${exception?.message}")
    }

    val childTwo = launch {
      repeat(1000) { i ->
        println("Child Coroutine 2: " +
            "$i. Crunching numbers [Beep.Boop.Beep]...")
        delay(500L)
      }
    }

    // Handle the exception thrown from `launch`
    // coroutine builder
    childTwo.invokeOnCompletion { exception ->
      println("Child Two: ${exception?.message}")
    }

  }
  delay(1200L)

  println("Calling cancelChildren() on the parentJob")
  parentJob.cancelChildren()

  println("parentJob isActive: ${parentJob.isActive}")
}
