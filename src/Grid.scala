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
    val tabAvailable : Array[Array[Boolean]] = Array.ofDim[Boolean](grid.length, grid(0).length)
    val possibilities : Array[Int] = Array(2, 2, 2, 2, 2, 2, 2, 2, 4, 4)

    //Verification of free spot
    var isFree: Boolean = false
    for(r <- grid.indices){
      for(c <- grid.indices){
        if(grid(r)(c).number == 0){
          isFree = true
        }
      }
    }

    if(!isFree){
      Dialog.endGame("GAME!")
    }

    //Create a new Tab of Boolean, if it's true, the slot is avaible for new number
    for (r <-grid.indices) {
      for (c <-grid(r).indices) {
        if (grid(r)(c).number == 0) {
          tabAvailable(r)(c) = true
        }
        else {
          tabAvailable(r)(c) = false
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
      isAvailable = tabAvailable(posX)(posY)
    } while(!isAvailable) //If the current spot is false, retry for a true one

    //Put a random number (between 2 and 4) in the random spot
    val rdmNumId : Int = Random.nextInt(9)
    grid(posX)(posY) = new Number(possibilities(rdmNumId), posX, posY)
  }

  //TODO For now i've let them blank, need to implements movement and grid display
  //first to be sure the left movement works properly before coding the remaining
  //direction
  def mergeUp() = {
    for(r <- grid.indices){
      var column : Array[Number] = new Array[Number](grid.length)
      //Get the column
      for(c <- grid(r).indices){
        column(c) = grid(c)(r)
      }
      //Filter the column
      var columnFiltered : Array[Number] = column.filter(_.number != 0)
      for(c <- columnFiltered.length-1 until 0 by -1){
        if(columnFiltered(c).number == columnFiltered(c-1).number){
          columnFiltered(c) = new Number(0, r, c)
          columnFiltered(c-1) = new Number(columnFiltered(c-1).number * 2, r, c)
        }
      }
      var mergeFiltered : Array[Number] = columnFiltered.filter(_.number != 0)
      var remainingZero : Array[Number] = Array.fill(grid(r).length - mergeFiltered.length)(new Number(0, 0, 0))
      //Get the tab of the column (the final one)
      var finalColumn: Array[Number] = Array.concat(mergeFiltered, remainingZero)
      //Put the new column in the grid
      for(c <- grid(r).indices){
        grid(c)(r) = finalColumn(c)
      }
    }
    updateNumPos()
    addRandomNumber()
  }
  def mergeDown() = {
    for(r <- grid.indices){
      var column : Array[Number] = new Array[Number](grid.length)
      //Get the column
      for(c <- grid(r).indices){
        column(c) = grid(c)(r)
      }
      //Filter the column
      var columnFiltered : Array[Number] = column.filter(_.number != 0)
      //Merge if two same number
      for(c <- 0 until columnFiltered.length-1){
        if(columnFiltered(c).number == columnFiltered(c+1).number){
          columnFiltered(c) = new Number(0, r, c)
          columnFiltered(c+1) = new Number(columnFiltered(c+1).number * 2, r, c)
        }
      }
      var mergeFiltered : Array[Number] = columnFiltered.filter(_.number != 0)
      var remainingZero : Array[Number] = Array.fill(grid(r).length - mergeFiltered.length)(new Number(0, 0, 0))
      //Get the tab of the column (the final one)
      var finalColumn: Array[Number] = Array.concat(remainingZero, mergeFiltered)
      //Put the new column in the grid
      for(c <- grid(r).indices){
        grid(c)(r) = finalColumn(c)
      }
    }
    updateNumPos()
    addRandomNumber()
  }
  def mergeRight() = {
    for(r <- grid.indices){
      //Delete the 0 of the original line
      var lineFiltered: Array[Number] = grid(r).filter(_.number != 0)
      //Merge if two numbers are the same
      for(c <- 0 until lineFiltered.length-1){
        if(lineFiltered(c).number == lineFiltered(c+1).number){
          //Fill the void spot with a new 0
          lineFiltered(c) = new Number(0, r, c)
          lineFiltered(c+1) = new Number(lineFiltered(c+1).number * 2, r, c)
        }
      }
      var mergeFiltered: Array[Number] = lineFiltered.filter(_.number != 0)
      var remainingZero: Array[Number] = Array.fill(grid(r).length - mergeFiltered.length)(new Number(0, 0, 0))
      grid(r) = Array.concat(remainingZero, mergeFiltered)
    }
    updateNumPos()
    addRandomNumber()
  }


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

    }
    updateNumPos()
    addRandomNumber()
  }

  def printArray(f: Array[Array[Number]]): Unit = {
    for (i <- f.indices) {
      for (j <- f(i).indices) {
        print(s"${f(i)(j)} ")
      }
      println("")
    }
    println("\n")
  }
}
