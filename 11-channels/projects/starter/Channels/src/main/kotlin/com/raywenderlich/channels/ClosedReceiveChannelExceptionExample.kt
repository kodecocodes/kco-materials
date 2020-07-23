package com.raywenderlich.channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() {

  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")

  val kotlinChannel = Channel<String>()

  runBlocking {
    launch {
      for (fruit in fruitArray) {
        // Conditional close
        if (fruit == "Grapes") {
          // Signal that closure of channel
          kotlinChannel.close()
        }

        kotlinChannel.send(fruit)
      }
    }

    repeat(fruitArray.size) {
      try {
        val fruit = kotlinChannel.receive()
        println(fruit)
      } catch (e: Exception) {
        println("Exception raised: ${e.javaClass.simpleName}")
      }
    }
    println("Done!")
  }
}