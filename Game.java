/* Nico Giuliani 06/13/2016, 08/30/2018 */

public class Game {
 
  public static void main(String[] args) {
    boolean keepPlaying = true;
    Jar mJar;

    // Until the player has chosen to quit playing, continue initiating new rounds
    while (keepPlaying) {
      mJar = Prompter.promptForSetup();
      mJar.fill();
      keepPlaying = Prompter.playGame();
    }

  }
}