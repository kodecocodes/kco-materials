package com.raywenderlich.sequence

fun main() {
  val sequence = sequenceExample().take(10)
  sequence.forEach {
    print("$it ")
  }
}

fun sequenceExample() = sequence {
  yieldAll(generateSequence(2) { it * 2 })
}