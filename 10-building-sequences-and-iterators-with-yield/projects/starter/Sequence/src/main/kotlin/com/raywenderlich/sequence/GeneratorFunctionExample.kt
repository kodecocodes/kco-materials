package com.raywenderlich.sequence

fun main() {
  val sequence = generatorFib().take(8)
  sequence.forEach {
    println("$it")
  }
}

fun generatorFib() = sequence {
  print("Suspending...")
  yield(0)
  var cur = 0
  var next = 1
  while (true) {
    print("Suspending...")
    yield(next)
    val tmp = cur + next
    cur = next
    next = tmp
  }
}