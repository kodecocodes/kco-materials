package producer

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

fun main() {
//  // You're using isClosedForSend and offer
//  val producer = GlobalScope.produce(capacity = 10) {
//    while (isActive) {
//      if (!isClosedForSend) {
//        val number = Random.nextInt(0, 20)
//        if (offer(number)) {
//          println("$number sent")
//        } else {
//          println("$number discarded")
//        }
//      }
//    }
//  }

//  // Using isFull and offer
//  val producer = GlobalScope.produce(capacity = 10) {
//    while (isActive) {
//      val number = Random.nextInt(0, 20)
//      if (!isFull) {
//        if (offer(number)) {
//          println("$number sent")
//        }
//      } else {
//        println("$number discarded")
//      }
//    }
//  }
//  Thread.sleep(30L)

  // Using blocking send and isActive
  val producer = GlobalScope.produce(capacity = 10) {
    while (isActive) {
      val number = Random.nextInt(0, 20)
      send(number)
      println("$number sent")
    }
  }

//  // Consumer using isClosedForReceive and poll
//  while (!producer.isClosedForReceive) {
//    val number = producer.poll()
//
//    if (number != null) {
//      println("$number received")
//    }
//  }

//  // Consumer using consumeEach
//  GlobalScope.launch {
//    producer.consumeEach { println("$it received") }
//  }

  // Producer using receive
  GlobalScope.launch {
    while (isActive) {
      val value = producer.receive()
      println("$value received")
    }
  }

  Thread.sleep(30L)

}