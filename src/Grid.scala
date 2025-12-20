class Grid (size: Int = 4) {

  var grid: Array[Array[Number]] = Array.ofDim(size, size)

  //Fill the grid with 0s as default value
  for (r <- grid.indices) {
    for (c <- grid(r).indices) {
      grid(r)(c) = new Number(0, r, c)
    }
  }

  //TODO For now i've let them blank
  def mergeUp() = ???
  def mergeDown() = ???
  def mergeRight() = ???
  def mergeLeft() = ???
}
