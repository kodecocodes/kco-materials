package com.raywenderlich.sequence

fun main() {
  val sequence = generatorFib().take(8)
  sequence.forEach {
    println("$it")
  }
}

fun generatorFib() = sequence {
  print("Suspending...")
  yield(0L)
  var cur = 0L
  var next = 1L
  while (true) {
    print("Suspending...")
    yield(next)
    val tmp = cur + next
    cur = next
    next = tmp
  }
}