object Play extends App{
  var game : Game = new Game(Dialog.getSizeGame("Choose between these"))
  game.startNewGame()
  game.drawBoard()
}
