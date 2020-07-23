package actor

fun main() {

  val items = listOf(
      Package(1, "coffee"),
      Package(2, "chair"),
      Package(3, "sugar"),
      Package(4, "t-shirts"),
      Package(5, "pillowcases"),
      Package(6, "cellphones"),
      Package(7, "skateboard"),
      Package(8, "cactus plants"),
      Package(9, "lamps"),
      Package(10, "ice cream"),
      Package(11, "rubber duckies"),
      Package(12, "blankets"),
      Package(13, "glass")
  )

  val initialRobot = WarehouseRobot(1, items)

  initialRobot.organizeItems()
  Thread.sleep(5000)
}
