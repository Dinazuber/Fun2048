import hevs.graphics.FunGraphics

import java.awt.event.{KeyEvent, KeyListener}
import java.awt.{Color, Font}
import scala.io.Source
import scala.util.Random

 object Game {
  private val windowWidth : Int = 1080
  private val headerHeight : Int = 250
  private var window : FunGraphics = new FunGraphics(windowWidth, windowWidth + headerHeight, "Fun2048")

  private val numCells : Int =  Dialog.getSizeGame("Choose between 4, 5, 6")
  private val gridWidth : Int = 960
  private val widthCell : Int = gridWidth / numCells

  private var grid: Grid = _

  var keyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {

    }

    override def keyPressed(e: KeyEvent): Unit = {

    }

    override def keyReleased(e: KeyEvent): Unit = {
      e.getKeyCode match {
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

  def resetGame(isFree: Boolean) : Unit = {
    if(!isFree) {
      window.clear()
      startNewGame()
      grid.resetAvailableGrid()
    }
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

    //Draw header
    window.drawString(offsetStart, offsetStart*2, "2048", "Arial", 0, 100, Color.black, 1, 2)
    window.setColor(new Color(165, 148, 131))
    val labelWidth : Int = 400
    val labelHeight : Int = 70
    window.drawFillRect(gridWidth - labelWidth + offsetStart, offsetStart -10, labelWidth, labelHeight)
    window.drawString(gridWidth - labelWidth + offsetStart + labelHeight/2, offsetStart - 25 + labelHeight, "R : To rollback", "Arial", 0, 50, Color.white, 2, 2)


    window.drawFillRect(offsetStart, offsetStart * 3, labelWidth, labelHeight)
    window.drawString(offsetStart + 20, offsetStart * 3 + 5, s"Score : ${grid.currScore}", "Arial", 0, 50, Color.black, 1, 1)

    window.drawFillRect(gridWidth - labelWidth + offsetStart, offsetStart * 3, labelWidth, labelHeight)
    window.drawString(gridWidth - labelWidth + offsetStart + labelHeight/2, offsetStart * 3 - 20 + labelHeight, s"Highest : ${grid.currScore}", "Arial", 0, 50, Color.black, 2, 2)


    //Draw cell background
    //Draw background color
    for (r <- 0 until numCells) {
      for (c <- 0 until numCells) {
        val xPos: Int = offsetStart + c*widthCell
        val yPos: Int = offsetStart + r*widthCell + headerHeight
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
        drawWithStroke(stroke, varPos, c + headerHeight) //Draw Vertical lines
        drawWithStroke(stroke, c, varPos + headerHeight) //Draw Horizontal lines
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

   def readJson(path: String) = {
     val source = Source.fromFile(path)
     val content = source.mkString
   }

}