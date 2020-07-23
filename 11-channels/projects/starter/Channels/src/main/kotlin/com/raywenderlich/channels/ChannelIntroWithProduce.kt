package com.raywenderlich.channels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
fun main() {

  val fruitArray = arrayOf("Apple", "Banana", "Pear", "Grapes", "Strawberry")

  fun produceFruits() = GlobalScope.produce<String> {
    for (fruit in fruitArray) {
      send(fruit)

      // Conditional close
      if (fruit == "Pear") {
        // Signal that closure of channel
        close()
      }
    }
  }

  runBlocking {
    val fruits = produceFruits()
    fruits.consumeEach { println(it) }
    println("Done!")
  }
}