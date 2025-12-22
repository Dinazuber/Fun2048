import hevs.graphics.FunGraphics

import java.awt.{Color, Font}

class Game {
  val windowWidth : Int = 1080
  var window : FunGraphics = new FunGraphics(windowWidth, windowWidth, "Fun2048")

  var numCells : Int = 4
  val gridWidth : Int = 960
  val widthCell : Int = gridWidth / numCells

  var grid: Grid = _


  /** Permet de lancer une nouvelle partie */
  def startNewGame(): Unit = {
    grid = new Grid(numCells)
    grid.resetGrid()
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
        window.drawString(xPos + widthCell/2, (yPos+widthCell/2)-30, s"${grid.getCellNum(c, r)}", "Arial", 0, widthCell/4, Color.black, 0, 1)

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