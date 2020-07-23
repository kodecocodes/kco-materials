package com.raywenderlich.channels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


@ExperimentalCoroutinesApi
fun main() {

  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")

  val channel: ReceiveChannel<String> = GlobalScope.produce {
    for (fruit in fruitArray) {
      channel.send(fruit)
      delay(200)
    }
  }

  runBlocking {
    channel.consumeEach {
      println(it)
    }
    println("Done!")
  }
}