package com.raywenderlich.sequence

fun main() {
  val sequence = singleValueExample()
  sequence.forEach {
    println(it)
  }
}

fun singleValueExample() = sequence {
  println("Printing first value")
  yield("Apple")
  println("Printing second value")
  yield("Orange")
  println("Printing third value")
  yield("Banana")
}