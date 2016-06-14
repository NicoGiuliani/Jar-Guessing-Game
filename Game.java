/* Nico Giuliani 06/13/2016 */

public class Game {
 
  public static void main(String[] args) {
    boolean keepPlaying = true;
    Jar mJar;
    
    while (keepPlaying) {
      mJar = Prompter.promptForSetup();
      mJar.fill();
      keepPlaying = Prompter.playGame();
    }

  }
}