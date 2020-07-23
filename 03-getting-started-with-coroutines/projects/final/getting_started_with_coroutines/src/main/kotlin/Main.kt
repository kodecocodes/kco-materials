import kotlinx.coroutines.*

fun main() {
//  (1..10000).forEach {
//    val threadName = Thread.currentThread().name
//    GlobalScope.launch { println("$it printed on thread $threadName") }
//  }
//
//  Thread.sleep(1000)


//  GlobalScope.launch {
//    println("Hello coroutine!")
//    delay(500)
//    println("Right back at ya!")
//  }
//
//  Thread.sleep(1000)


//  var isDoorOpen = false
//
//  println("Unlocking the door... please wait.\n")
//  GlobalScope.launch {
//    delay(3000)
//
//    isDoorOpen = true
//  }
//
//  GlobalScope.launch {
//    repeat(4) {
//      println("Trying to open the door...\n")
//      delay(800)
//
//      if (isDoorOpen) {
//        println("Opened the door!\n")
//      } else {
//        println("The door is still locked\n")
//      }
//    }
//  }
//
//  Thread.sleep(5000)


//  val job1 = GlobalScope.launch(start = CoroutineStart.LAZY) {
//    delay(200)
//    println("Pong")
//    delay(200)
//  }
//
//  GlobalScope.launch {
//    delay(200)
//    println("Ping")
//    job1.join()
//    println("Ping")
//    delay(200)
//  }
//  Thread.sleep(1000)


//  with(GlobalScope) {
//    val parentJob = launch {
//      delay(200)
//      println("I'm the parent")
//      delay(200)
//    }
//    launch(parentJob) {
//      delay(200)
//      println("I'm a child")
//      delay(200)
//    }
//    if (parentJob.children.iterator().hasNext()) {
//      println("The Job has children ${parentJob.children}")
//    } else {
//      println("The Job has NO children")
//    }
//    Thread.sleep(1000)
//  }

  GlobalScope.launch {
    val bgThreadName = Thread.currentThread().name
    println("I'm Job 1 in thread $bgThreadName")
    delay(200)
    GlobalScope.launch(Dispatchers.Main) {
      val uiThreadName = Thread.currentThread().name
      println("I'm Job 2 in thread $uiThreadName")
    }
  }
  Thread.sleep(1000)

}