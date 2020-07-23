package com.raywenderlich.broadcastchannels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
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
      // Send data in channel
      kotlinChannel.send(fruitArray[0])
    }

    // Consumers
    GlobalScope.launch {
      kotlinChannel.consumeEach { value ->
        println("Consumer 1: $value")
      }
    }
    GlobalScope.launch {
      kotlinChannel.consumeEach { value ->
        println("Consumer 2: $value")
      }
    }

    // Wait for a keystroke to exit the program
    println("Press a key to exit...")
    readLine()

    // Close the channel so as to cancel the consumers on it too
    kotlinChannel.close()
  }
}
