import javax.swing.{JFrame, JTextField, JOptionPane}
object Dialog {
  def getSizeGame(message: String): Int = {
    val options = new JTextField()
    val frame = new JFrame(message)

    JOptionPane.showMessageDialog(frame, options, message, JOptionPane.PLAIN_MESSAGE)
    val s = new String(options.getText)
    if(s == "4" || s == "5" || s == "6"){
      s.toInt
    } else {
      getSizeGame("Choisissez une taille pour le jeu (4, 5 ou 6)")
    }
  }
}
