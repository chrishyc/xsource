package demo

class Aa[T, S](val first: T, val second: S) {
  def getMiddle[T](a: Array[T]) = a(a.length / 2)

}
