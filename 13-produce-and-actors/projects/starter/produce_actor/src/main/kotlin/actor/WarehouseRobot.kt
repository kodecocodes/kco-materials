package actor

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

class WarehouseRobot(private val id: Int,
                     private var packages: List<Package>) {

  companion object {
    private const val ROBOT_CAPACITY = 3
  }

  fun organizeItems() {
    val itemsToProcess = packages.take(ROBOT_CAPACITY)
    val leftoverItems = packages.drop(ROBOT_CAPACITY)

    packages = itemsToProcess

    processItems(itemsToProcess)

    val packageIds = packages.map { it.id }
        .fold("") { acc, item -> "$acc$item " }

    if (leftoverItems.isNotEmpty()) {
      GlobalScope.launch {
        val helperRobot = WarehouseRobot(id.inc(), leftoverItems)

        helperRobot.organizeItems()
      }
    }

    println("Robot #$id processed following packages:$packageIds")
  }

  private fun processItems(items: List<Package>) {
    val actor = GlobalScope.actor<Package>(
        capacity = ROBOT_CAPACITY) {

      var hasProcessedItems = false

      while (!packages.isEmpty()) {
        val currentPackage = poll()

        currentPackage?.run {
          organize(this)

          packages -= currentPackage
          hasProcessedItems = true
        }

        if (hasProcessedItems && currentPackage == null) {
          cancel()
        }
      }
    }

    items.forEach { actor.offer(it) }
  }
}

private fun organize(warehousePackage: Package) =
    println("Organized package " +
        "${warehousePackage.id}:" +
        warehousePackage.name)
