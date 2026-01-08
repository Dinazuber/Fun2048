import java.io.File
import javax.sound.sampled.AudioSystem

object SoundPlayer {

  /**
   * Play a sound
   * @param path Relative path to a .wav file
   */
  def play(path: String) = {
    val file = new File(path)
    val url = file.toURI.toURL
    val audioIn = AudioSystem.getAudioInputStream(url)
    val clip = AudioSystem.getClip
    clip.open(audioIn)
    clip.start()
  }

}
