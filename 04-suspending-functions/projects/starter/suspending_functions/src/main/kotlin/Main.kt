fun main() {
  val user = getUserStandard("101")

  println(user)
}

fun getUserStandard(userId: String): User {
  Thread.sleep(1000)

  return User(userId, "Filip")
}