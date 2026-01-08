import hevs.graphics.FunGraphics

import javax.swing.{JButton, JFrame, JOptionPane, JTextField}
object Dialog {

  /**
   * Display a popup for the user to select the amount of cell per lines
   * @param message Message displayed on the popup
   * @return
   */
  def getSizeGame(message: String): Int = {
    var possibleValues = Array[AnyRef]("4", "5", "6")

    var selectedValue = JOptionPane.showInputDialog(
      null,
      "Choose the size of the game",
      "Input",
      JOptionPane.INFORMATION_MESSAGE,
      null,
      possibleValues,
      possibleValues(0))
    if(selectedValue == null){
      System.exit(1)
    }
    return selectedValue.toString.toInt
  }

  /**
   * Popup to inform the player that the game ended
   * @param message Message displayed on the popup
   * @param game Game to start
   */
  def endGame(message: String) : Unit = {
    val frame = new JFrame(message)

    val result = JOptionPane.showConfirmDialog(frame, "Restart game", "End game", JOptionPane.YES_NO_OPTION)
    if(result == JOptionPane.YES_OPTION){
      //game.clear()
      Game.startNewGame()
    } else {
      //Finish the game
      System.exit(0)
    }
  }
}
