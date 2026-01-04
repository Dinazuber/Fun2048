import javax.swing.{JButton, JFrame, JOptionPane, JTextField}
object Dialog {
  def getSizeGame(message: String): Int = {
    val options = new JTextField()
    val frame = new JFrame(message)

    JOptionPane.showMessageDialog(frame, options, message, JOptionPane.PLAIN_MESSAGE)
    val s = new String(options.getText)
    s match {
      case "4" => return 4
      case "5" => return 5
      case "6" => return 6
      case _ => getSizeGame("Enter the size of the game (4, 5 or 6)")
    }
  }

  def endGame(message: String) : Unit = {
    val frame = new JFrame(message)

    val result = JOptionPane.showConfirmDialog(frame, "Restart game", "End game", JOptionPane.YES_NO_OPTION)
    if(result == JOptionPane.YES_OPTION){
      //If the player wants to restart the game
      var game: Game = new Game
      game.startNewGame()
    } else {
      //Finish the game
      System.exit(1)
    }
  }
}
