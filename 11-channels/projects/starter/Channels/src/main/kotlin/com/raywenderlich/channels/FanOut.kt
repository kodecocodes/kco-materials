package com.raywenderlich.channels

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

typealias Predicate<E> = (E) -> Boolean

typealias Rule<E> = Pair<Channel<E>, Predicate<E>>


/**
 * This is a class which implements a demultiplexer. It send each items
 * into the first channel with a predicate that evaluates true for it
 */
class Demultiplexer<E>(vararg val rules: Rule<E>) {

  suspend fun consume(receiveChannel: ReceiveChannel<E>) {
    for (item in receiveChannel) {
      // Receive the data from the channel
      for (rule in rules) {
        // Check every rule until you find a successful one
        if (rule.second(item)) {
          rule.first.send(item)
        }
      }
    }
    // When here the channel has been closed so you can close the
    // demultiplexed channels
    closeAll()
  }

  // Closes all the demultiplexed channels
  private fun closeAll() {
    rules.forEach { it.first.close() }
  }
}


@ExperimentalCoroutinesApi
fun main() {

  data class Fruit(override val name: String, override val color: String) : Item
  data class Vegetable(override val name: String, override val color: String) : Item

  // ------------ Helper Methods ------------
  fun isFruit(item: Item) = item is Fruit

  fun isVegetable(item: Item) = item is Vegetable


  // Produces a finite number of items
  // which are either a fruit or vegetable
  fun produceItems(): ArrayList<Item> {
    val itemsArray = ArrayList<Item>()
    itemsArray.add(Fruit("Apple", "Red"))
    itemsArray.add(Vegetable("Zucchini", "Green"))
    itemsArray.add(Fruit("Grapes", "Green"))
    itemsArray.add(Vegetable("Radishes", "Red"))
    itemsArray.add(Fruit("Banana", "Yellow"))
    itemsArray.add(Fruit("Cherries", "Red"))
    itemsArray.add(Vegetable("Broccoli", "Green"))
    itemsArray.add(Fruit("Strawberry", "Red"))
    itemsArray.add(Vegetable("Red bell pepper", "Red"))
    return itemsArray
  }

  runBlocking {

    // Initialize the Channels
    val kotlinChannel = Channel<Item>()

    val fruitsChannel = Channel<Item>()
    val vegetablesChannel = Channel<Item>()


    launch {
      produceItems().forEach {
        kotlinChannel.send(it)
      }

      kotlinChannel.close()
    }


    val typeDemultiplexer = Demultiplexer(
        fruitsChannel to { item: Item -> isFruit(item) },
        vegetablesChannel to { item: Item -> isVegetable(item) }
    )

    launch {
      typeDemultiplexer.consume(kotlinChannel)
    }

    launch {
      for (item in fruitsChannel) {
        // Consume fruitsChannel
        println("${item.name} is a fruit")
      }
    }

    launch {
      for (item in vegetablesChannel) {
        // Consume vegetablesChannel
        println("${item.name}  is a vegetable")
      }
    }
  }
}
