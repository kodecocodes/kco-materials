package com.raywenderlich.sequence


fun main() {
  val sequence = iterableExample()
  sequence.forEach {
    print("$it ")
  }
}

fun iterableExample() = sequence {
  yieldAll(1..5)
}