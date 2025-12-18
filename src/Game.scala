import hevs.graphics.FunGraphics

class Game {
  val screenWidth : Int = 1080
  var display : FunGraphics = new FunGraphics(screenWidth, screenWidth)
  val height : Int = 4
  val widthGameplay : Int = 960

  val widthCell : Int = widthGameplay / height

}