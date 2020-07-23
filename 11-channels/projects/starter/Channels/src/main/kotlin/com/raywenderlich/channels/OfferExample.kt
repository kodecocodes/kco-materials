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
        val wasSent = kotlinChannel.offer(fruit)
        if (wasSent) {
          println("Sent: $fruit")
        } else {
          println("$fruit wasn't sent")
        }
      }
      kotlinChannel.close()
    }

    for (fruit in kotlinChannel) {
      println("Received: $fruit")
    }
    println("Done!")
  }
}