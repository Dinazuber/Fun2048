import java.awt.Color

class Number {

  var numéro: Int = _
  var xPosGrid: Int = _ //Define it's X position (index) on the grid
  var yPosGrid: Int = _ //Define it's Y position (index) on the grid
  var bgColor = new Color(255, 255, 255)

  def this(num: Int = 0, xpos: Int, ypos: Int) = {
    this()
    numéro = num
    xPosGrid = xpos
    yPosGrid = ypos
    setColor(numéro)
  }

  private def setColor(number: Int) = {
    number match {
      //TODO Need to change the colors to the matching one from the OG game
      case 0 => bgColor = new Color(0, 0, 0)

      case 2 => bgColor = new Color(0, 0, 0)

      case 4 => bgColor = new Color(0, 0, 0)

      case 8 => bgColor = new Color(0, 0, 0)

      case 16 => bgColor = new Color(0, 0, 0)

      case 32 => bgColor = new Color(0, 0, 0)

      case 64 => bgColor = new Color(0, 0, 0)

      case 128 => bgColor = new Color(0, 0, 0)

      case 256 => bgColor = new Color(0, 0, 0)

      case 512 => bgColor = new Color(0, 0, 0)

      case 1024 => bgColor = new Color(0, 0, 0)

      case 2048 => bgColor = new Color(0, 0, 0)

      case _ => bgColor = new Color(0, 0, 0) //This one is for all numbs above 2048
    }
  }

}
