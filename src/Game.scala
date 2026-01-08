import hevs.graphics.FunGraphics

import java.awt.event.{KeyEvent, KeyListener}
import java.awt.{Color, Font}
import scala.util.Random

 object Game {
  private val windowWidth : Int = 1080
  private var window : FunGraphics = new FunGraphics(windowWidth, windowWidth, "Fun2048")

  private val numCells : Int =  Dialog.getSizeGame("Choose between 4, 5, 6")
  private val gridWidth : Int = 960
  private val widthCell : Int = gridWidth / numCells

  private var grid: Grid = _

  var keyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {

    }

    override def keyPressed(e: KeyEvent): Unit = {

    }

    //TODO if the player presses "R", it'll refresh the game
    override def keyReleased(e: KeyEvent): Unit = {
      e.getKeyCode match {
        case KeyEvent.VK_R => {
          resetGame()
        }
        case KeyEvent.VK_BACK_SPACE => {
          rewindGame()
        }
        case KeyEvent.VK_UP => {
          grid.mergeUp()
          resetFunGrid()
          if(grid.addRandomNumber()){
            drawBoard()
          } else {
            Dialog.endGame("GAME!", window)
          }
        }
        case KeyEvent.VK_RIGHT => {
          grid.mergeRight()
          resetFunGrid()
          if(grid.addRandomNumber()){
            drawBoard()
          } else {
            Dialog.endGame("GAME!", window)
          }
        }
        case KeyEvent.VK_DOWN => {
          grid.mergeDown()
          resetFunGrid()
          if(grid.addRandomNumber()){
            drawBoard()
          } else {
            Dialog.endGame("GAME!", window)
          }
        }
        case KeyEvent.VK_LEFT => {
          grid.mergeLeft()
          resetFunGrid()
          if(grid.addRandomNumber()){
            drawBoard()
          } else {
            Dialog.endGame("GAME!", window)
          }
        }
        case _ => {

        }
      }
    }
  }
  window.setKeyManager(keyListener)

  def resetFunGrid() : Unit = {
    window.clear()
  }

  def resetGame() : Unit = {
    window.clear()
    startNewGame()
    grid.resetAvailableGrid()
  }

   /**
    * Gives the option to rewind one action of the game
    */
   def rewindGame() : Unit = {
     window.clear()
     grid.getPreviousGrid()
     drawBoard()
   }

  /** Permet de lancer une nouvelle partie */
  def startNewGame(): Unit = {
    grid = new Grid(numCells)
    grid.resetGrid()
    resetFunGrid()
    val rdmStart : Int = Random.nextInt(3)
    for(i <- 0 to rdmStart){
      grid.addRandomNumber()
    }
    drawBoard()
  }

  /** Dessine l'ensemble du plateau de jeu */
  def drawBoard() = {
    val offsetStart: Int = (windowWidth - gridWidth) / 2
    //Draw background color
    for (r <- 0 until numCells) {
      for (c <- 0 until numCells) {
        val xPos: Int = offsetStart + c*widthCell
        val yPos: Int = offsetStart + r*widthCell
        window.setColor(grid.getCellColor(c, r))
        //window.setColor(Color.red)
        window.drawFillRect(xPos, yPos, widthCell, widthCell)
        if (grid.getCellNum(c, r) != 0) {
          window.drawString(xPos + widthCell/2, (yPos+widthCell/2)-30, s"${grid.getCellNum(c, r)}", "Arial", 0, widthCell/4, Color.black, 0, 1)
        }
      }
    }
    //draw Grid with stroke
    window.setColor(Color.black)
    val stroke: Int = 3
    for (x <- 0 to numCells) {
      var varPos: Int = offsetStart + x*widthCell
      val PosStart: Int = offsetStart
      val PosEnd: Int = offsetStart + numCells*widthCell
      for (c <- PosStart to PosEnd) {
        drawWithStroke(stroke, varPos, c) //Draw Vertical lines
        drawWithStroke(stroke, c, varPos) //Draw Horizontal lines
      }
    }
  }

  /**
   *  Permet de dessiner un pixel avec une largeur de pinceau définit
   * @param brushSize Taille du pinceau
   * @param xPos  Position x où dessiner
   * @param yPos  Position y où dessiner
   */
  def drawWithStroke(brushSize: Int, xPos: Int, yPos: Int) = {
    for (i <- -1*brushSize to brushSize) {
      for (j <- -1*brushSize to brushSize) {
        window.setPixel(xPos+j, yPos+i)
      }
    }
  }

}