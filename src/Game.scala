import hevs.graphics.FunGraphics

class Game {
  val windowWidth : Int = 1080
  var window : FunGraphics = new FunGraphics(windowWidth, windowWidth)

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
    val offsetStart: Int = windowWidth - gridWidth
    //Draw background color
    for (r <- 0 until numCells) {
      for (c <- 0 until numCells) {
        val xPos: Int = offsetStart + c*widthCell
        val yPos: Int = offsetStart + r*widthCell
        window.setColor(grid.getCellColor(c, r))
        window.drawRect(xPos, yPos, widthCell, widthCell)
      }
    }
    //Draw grid
    //Draw Number
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