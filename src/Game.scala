import hevs.graphics.FunGraphics

import java.awt.event.{KeyEvent, KeyListener}
import java.awt.{Color, Font}
import java.io.{BufferedOutputStream, FileOutputStream, PrintStream, PrintWriter}
import scala.io.Source
import scala.util.Random


 object Game {
  private val windowWidth : Int = 1080
  private val headerHeight : Int = 250
  private var window : FunGraphics = new FunGraphics(windowWidth, windowWidth + headerHeight, "Fun2048")

  private val numCells : Int =  Dialog.getSizeGame("Choose between 4, 5, 6")
  private val gridWidth : Int = 960
  private val widthCell : Int = gridWidth / numCells
  private val scoreFile : String = "./res/highest_score.csv"
  resetHighestScore(scoreFile)

  private var grid: Grid = _

  var keyListener = new KeyListener {
    override def keyTyped(e: KeyEvent): Unit = {

    }

    override def keyPressed(e: KeyEvent): Unit = {

    }

    //Manage the key pressed events
    override def keyReleased(e: KeyEvent): Unit = {
      e.getKeyCode match {
        case KeyEvent.VK_R => {
          resetGame()
        }
        case KeyEvent.VK_BACK_SPACE => {
          rewindGame()
        }
        case KeyEvent.VK_UP => {
          SoundPlayer.play("res/pop.wav")
          grid.mergeUp()
          clearWindow()
          if(grid.addRandomNumber()){
            drawBoard()
          } else {
            SoundPlayer.play("res/loosing.wav")
            updateHighestScore(grid.currScore, scoreFile)
            Dialog.endGame("GAME!")
          }
        }
        case KeyEvent.VK_RIGHT => {
          SoundPlayer.play("res/pop.wav")
          grid.mergeRight()
          clearWindow()
          if(grid.addRandomNumber()){
            drawBoard()
          } else {
            SoundPlayer.play("res/loosing.wav")
            updateHighestScore(grid.currScore, scoreFile)
            Dialog.endGame("GAME!")
          }
        }
        case KeyEvent.VK_DOWN => {
          SoundPlayer.play("res/pop.wav")
          grid.mergeDown()
          clearWindow()
          if(grid.addRandomNumber()){
            drawBoard()
          } else {
            SoundPlayer.play("res/loosing.wav")
            updateHighestScore(grid.currScore, scoreFile)
            Dialog.endGame("GAME!")
          }
        }
        case KeyEvent.VK_LEFT => {
          SoundPlayer.play("res/pop.wav")
          grid.mergeLeft()
          clearWindow()
          if(grid.addRandomNumber()){
            drawBoard()
          } else {
            SoundPlayer.play("res/loosing.wav")
            updateHighestScore(grid.currScore, scoreFile)
            Dialog.endGame("GAME!")
          }
        }
        case _ => {

        }
      }
    }
  }
  window.setKeyManager(keyListener)


   /**
    * Clear the window
    */
   def clearWindow() : Unit = {
    window.clear()
  }


   /**
    * Reset and start a new game
    */
  def resetGame() : Unit = {
    clearWindow()
    startNewGame()
    grid.resetAvailableGrid()
  }

   /**
    * Gives the option to rewind one action of the game
    */
   def rewindGame() : Unit = {
     clearWindow()
     grid.getPreviousGrid()
     drawBoard()
   }

  /** Allow to start a new game */
  def startNewGame(): Unit = {
    grid = new Grid(numCells)
    grid.resetGrid()
    clearWindow()
    val rdmStart : Int = Random.nextInt(3)
    for(i <- 0 to rdmStart){
      grid.addRandomNumber()
    }
    drawBoard()
  }

  /** Draw the entire board in the window */
  def drawBoard() = {

    val offsetStart: Int = (windowWidth - gridWidth) / 2

    //Draw header
    window.drawString(offsetStart, offsetStart*2, "2048", "Arial", 0, 100, Color.black, 1, 2)
    window.setColor(new Color(165, 148, 131))
    val labelWidth : Int = 400
    val labelHeight : Int = 70
    window.drawFillRect(gridWidth - labelWidth + offsetStart, offsetStart -10, labelWidth, labelHeight)
    window.drawString(gridWidth - labelWidth + offsetStart + labelHeight/2, offsetStart - 25 + labelHeight, "R : To restart", "Arial", 0, 50, Color.white, 2, 2)


    window.drawFillRect(offsetStart, offsetStart * 3, labelWidth, labelHeight)
    window.drawString(offsetStart + 20, offsetStart * 3 + 5, s"Score : ${grid.currScore}", "Arial", 0, 50, Color.white, 1, 1)

    window.drawFillRect(gridWidth - labelWidth + offsetStart, offsetStart * 3, labelWidth, labelHeight)
    val highest_score : Int = getHighestScore(scoreFile)
    window.drawString(gridWidth - labelWidth + offsetStart + labelHeight/2, offsetStart * 3 - 20 + labelHeight, s"Highest : ${highest_score}", "Arial", 0, 50, Color.white, 2, 2)


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
   *  Draw a pixel using a brush with a stroke
   * @param brushSize Size of the stroke
   * @param xPos  X pos to draw
   * @param yPos  Y pos to draw
   */
  def drawWithStroke(brushSize: Int, xPos: Int, yPos: Int) = {
    for (i <- -1*brushSize to brushSize) {
      for (j <- -1*brushSize to brushSize) {
        window.setPixel(xPos+j, yPos+i)
      }
    }
  }

   def getHighestScore(path: String) : Int = {
     var highest_score : Int = 0
     try {
       val source = Source.fromFile(path)
       val content = source.getLines().toArray
       for(i <- content.indices){
         highest_score = content(i).toInt
       }
       highest_score
     } catch {
       case e: Exception => {
         println(s"Error while opening file ${path}")
         0
       }
     }
   }

   def resetHighestScore(path: String) : Unit = {
     val writer = new PrintWriter(path)
     writer.println(0)
     writer.close()
   }

   def updateHighestScore(score: Int, path: String) : Unit = {
     val current_highest_score : Int = getHighestScore(path)
     if(score > current_highest_score) {
       val writer = new PrintWriter(path)
       writer.println(score)
       writer.close()
     }
   }

}