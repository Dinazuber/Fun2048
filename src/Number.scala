import java.awt.Color

class Number {

  var number: Int = 0
  var xPosGrid: Int = _ //Define it's X position (index) on the grid
  var yPosGrid: Int = _ //Define it's Y position (index) on the grid
  var bgColor = new Color(255, 255, 255)

  /**
   *
   * @param num Number displayed in the cell
   * @param xpos X Index inside the grid
   * @param ypos Y Index inside the grid
   */
  def this(num: Int = 0, xpos: Int, ypos: Int) = {
    this()
    number = num
    xPosGrid = xpos
    yPosGrid = ypos
    setColor(number)
  }

  /**
   * Set the background color for the cell according to a number
   * @param number Number that define the color
   */
  private def setColor(number: Int) = {
    number match {
      case 0 => bgColor = new Color(255, 255, 255)

      case 2 => bgColor = new Color(238, 228, 218)

      case 4 => bgColor = new Color(237, 244, 200)

      case 8 => bgColor = new Color(242, 177, 121)

      case 16 => bgColor = new Color(245, 149, 99)

      case 32 => bgColor = new Color(244, 123, 95)

      case 64 => bgColor = new Color(246, 94, 59)

      case 128 => bgColor = new Color(235, 205, 114)

      case 256 => bgColor = new Color(235, 202, 97)

      case 512 => bgColor = new Color(235, 198, 75)

      case 1024 => bgColor = new Color(235, 195, 63)

      case 2048 => bgColor = new Color(235, 192, 45)

      case _ => bgColor = new Color(179, 136, 45) //This one is for all numbs above 2048
    }
  }

  /**
   * Overrriden ToString method
   * @return The number displayed in the cell
   */
  override def toString: String = {
    s"${number}"
  }

}
