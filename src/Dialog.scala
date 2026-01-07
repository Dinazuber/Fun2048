import javax.swing.{JButton, JFrame, JOptionPane, JTextField}
object Dialog {
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

  def endGame(message: String) : Unit = {
    val frame = new JFrame(message)

    val result = JOptionPane.showConfirmDialog(frame, "Restart game", "End game", JOptionPane.YES_NO_OPTION)
    if(result == JOptionPane.YES_OPTION){
      //TODO If the player wants to restart the game
      var newGame : Game = new Game(Dialog.getSizeGame("Choose between these"))
      newGame.startNewGame()
      newGame.drawBoard()
    } else {
      //Finish the game
      System.exit(0)
    }
  }
}
