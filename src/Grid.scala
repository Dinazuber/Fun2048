import java.awt.Color
import scala.util.Random

class Grid (size: Int = 4) {

  var grid: Array[Array[Number]] = Array.ofDim(size, size)

  /** Vide la grille en mettant toutes les valeurs à 0 */
  def resetGrid() = {
    for (r <- grid.indices) {
      for (c <- grid(r).indices) {
        grid(r)(c) = new Number(0, r, c)
      }
    }
  }

  /** Permet de remettre à jour les positions des nombres */
  def updateNumPos() = {
    for (r <- grid.indices) {
      for (c <- grid(r).indices) {
        grid(r)(c).xPosGrid = c
        grid(r)(c).yPosGrid = r
      }
    }
  }

  def getCellColor(xPos: Int, yPos: Int): Color = {
    grid(yPos)(xPos).bgColor
  }

  def getCellNum(xPos: Int, yPos: Int): Int = {
    grid(yPos)(xPos).number
  }

  def addRandomNumber() = {
    var xAvailable: Array[Int] = new Array[Int](math.pow(grid.length, 2).toInt)
    var yAvailable: Array[Int] = new Array[Int](math.pow(grid.length, 2).toInt)
    for (r <-grid.indices) {
      for (c <-grid(r).indices) {
        if (grid(r)(c).number == 0) {
          xAvailable +:= c
          yAvailable +:= r
        }
        else {
          xAvailable +:= -1
          yAvailable +:= -1
        }
      }
    }
    var xPosRandom = Random.between(0, xAvailable.count(_ != -1))
    var yPosRandom = Random.between(0, yAvailable.count(_ != -1))
    val xPos = xAvailable.filter(_ != -1)(xPosRandom)
    val yPos = yAvailable.filter(_ != -1)(yPosRandom)
    grid(yPos)(xPos) = new Number(2, xPos, yPos)
  }

  //TODO For now i've let them blank, need to implements movement and grid display
  //first to be sure the left movement works properly before coding the remaining
  //direction
  def mergeUp() = ???
  def mergeDown() = ???
  def mergeRight() = ???


  /** Move all numbers to the left, merging them*/
  def mergeLeft() = {
    for (r <- grid.indices) {
      //Delete all 0s, cause all number to collapse to the left
      var lineFiltered: Array[Number] = grid(r).filter(_.number != 0)
      //Merge if two same following numbers
      for (c <- lineFiltered.length-1 until 0) {
          if (lineFiltered(c).number == lineFiltered(c-1).number) {
            //If there's gap, fill them with 0s to keep same array length
            lineFiltered(c) = new Number(0, r, c)
            lineFiltered(c-1) = new Number(lineFiltered(c-1).number * 2, r, c-1)
          }
      }
      var mergeFiltered: Array[Number] = lineFiltered.filter(_.number != 0)
      var remainingZero: Array[Number] = Array.fill(grid(r).length - mergeFiltered.length)(new Number(0, 0, 0))
      grid(r) = Array.concat(mergeFiltered, remainingZero)
      updateNumPos()
      addRandomNumber()
    }
  }
}
