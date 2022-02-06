package com.raywenderlich.sequence

fun main() {
  val list = listOf(1, 2, 3)

  list.filter {
    print("filter, ")
    it > 0
  }.map {
    print("map, ")
    it.toString()
  }.forEach {
    print("forEach, ")
  }
}