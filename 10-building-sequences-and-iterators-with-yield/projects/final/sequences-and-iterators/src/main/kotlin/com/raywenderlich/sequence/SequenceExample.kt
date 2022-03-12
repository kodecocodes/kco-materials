package com.raywenderlich.sequence

fun main() {
  val list = listOf(1, 2, 3)
  list.asSequence().filter {
    print("filter, ")
    it > 0
  }.map {
    print("map, ")
  }.forEach {
    print("forEach, ")
  }
}