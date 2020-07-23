package com.raywenderlich.broadcastchannels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
fun main() {

  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes",
      "Strawberry")

  val kotlinChannel = BroadcastChannel<String>(3)



  runBlocking {

    // Producer
    // Send data in channel
    kotlinChannel.apply {
      send(fruitArray[0])
      send(fruitArray[1])
      send(fruitArray[2])
    }

    // Consumers
    GlobalScope.launch {
      kotlinChannel.openSubscription().let { channel ->
        for (value in channel) {
          println("Consumer 1: $value")
        }
        // subscription will be closed
      }
    }
    GlobalScope.launch {
      kotlinChannel.openSubscription().let { channel ->
        for (value in channel) {
          println("Consumer 2: $value")
        }
        // subscription will be closed
      }
    }

    kotlinChannel.apply {
      send(fruitArray[3])
      send(fruitArray[4])
    }

    // Wait for a keystroke to exit the program
    println("Press a key to exit...")
    readLine()

    // Close the channel so as to cancel the consumers on it too
    kotlinChannel.close()
  }
}