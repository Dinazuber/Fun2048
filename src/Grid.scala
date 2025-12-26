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

  /**
   * Get the background color of a cell
   * @param xPos it's grid X index
   * @param yPos it's grid Y index
   * @return The background color
   */
  def getCellColor(xPos: Int, yPos: Int): Color = {
    grid(yPos)(xPos).bgColor
  }

  /**
   * Get the number assigned to a cell
   * @param xPos it's grid X index
   * @param yPos it's grid Y index
   * @return The number assigned
   */
  def getCellNum(xPos: Int, yPos: Int): Int = {
    grid(yPos)(xPos).number
  }

  /** Add a 2 at a random available spot on
   * the board */
  def addRandomNumber() = {
    val tabAvaible : Array[Array[Boolean]] = Array.ofDim[Boolean](grid.length, grid(0).length)
    val possibilities : Array[Int] = Array(2, 2, 2, 2, 2, 2, 2, 2, 4, 4)

    //Create a new Tab of Boolean, if it's true, the slot is avaible for new number
    for (r <-grid.indices) {
      for (c <-grid(r).indices) {
        if (grid(r)(c).number == 0) {
          tabAvaible(r)(c) = true
        }
        else {
          tabAvaible(r)(c) = false
        }
      }
    }

    //Get a random available position in the tab
    var isAvailable : Boolean = false
    var posX : Int = 0
    var posY : Int = 0
    do{
      posX = Random.nextInt(grid.length)
      posY = Random.nextInt(grid(0).length)
      isAvailable = tabAvaible(posX)(posY)
    } while(!isAvailable)

    //Put a random number (between 2 and 4) in the random spot
    val rdmNumId : Int = Random.nextInt(9)
    grid(posX)(posY) = new Number(possibilities(rdmNumId), posX, posY)
  }

  //TODO For now i've let them blank, need to implements movement and grid display
  //first to be sure the left movement works properly before coding the remaining
  //direction
  def mergeUp() = {
    println("Up")
  }
  def mergeDown() = ???
  def mergeRight() = ???


  /** Move all numbers to the left, merging them*/
  def mergeLeft() = {
    for (r <- grid.indices) {
      //Delete all 0s, cause all number to collapse to the left
      var lineFiltered: Array[Number] = grid(r).filter(_.number != 0)
      //Merge if two same following numbers
      for (c <- lineFiltered.length-1 until 0 by -1) {
          if (lineFiltered(c).number == lineFiltered(c-1).number) {
            //If there's gap, fill them with 0s to keep same array length
            lineFiltered(c) = new Number(0, r, c)
            lineFiltered(c-1) = new Number(lineFiltered(c-1).number * 2, r, c-1)
          }
      }
      var mergeFiltered: Array[Number] = lineFiltered.filter(_.number != 0)
      var remainingZero: Array[Number] = Array.fill(grid(r).length - mergeFiltered.length)(new Number(0, 0, 0))
      grid(r) = Array.concat(mergeFiltered, remainingZero)
      //updateNumPos()
      addRandomNumber()
    }
  }
}
