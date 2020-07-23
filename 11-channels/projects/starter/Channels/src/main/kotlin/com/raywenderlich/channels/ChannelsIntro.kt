package com.raywenderlich.channels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@ExperimentalCoroutinesApi
fun main() {

  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes",
      "Strawberry")

  val kotlinChannel = Channel<String>()

  runBlocking {

    // Producer
    GlobalScope.launch {
      for (fruit in fruitArray) {
        // Send data in channel
        kotlinChannel.send(fruit)

        // Conditional close
        if (fruit == "Pear") {
          // Signal the closure of channel
          kotlinChannel.close()
        }
      }
    }

    // Consumer
    // Print received values using `for` loop
    // (until the channel is closed)
    for (fruit in kotlinChannel) {
      println(fruit)
    }

    // Another way to iterate over the channel values
    // is to use the `isClosedForReceive` property in order to understand
    // if you have to continue consuming or not.
    /*while (!kotlinChannel.isClosedForReceive) {
      val fruit = kotlinChannel.receive()
      println(fruit)
    }*/

    println("Done!")
  }
}
