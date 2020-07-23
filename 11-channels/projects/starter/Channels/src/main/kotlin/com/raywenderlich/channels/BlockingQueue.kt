package com.raywenderlich.channels

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import java.util.concurrent.LinkedBlockingQueue

fun main(args: Array<String>) {
  val queue = LinkedBlockingQueue<Int>()
  runBlocking {

    launch {
      (1..5).forEach {
        queue.put(it)
        yield()
        println("Produced ${it}")
      }
    }

    launch {
      while (true) {
        println("Consumed ${queue.take()}")
        yield()
      }
    }
  }
}