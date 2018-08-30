/* Nico Giuliani 06/13/2016, 08/30/2018 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Prompter {
  private static Jar mJar;
  private static int mGuesses;
  private static String mScoreBoard = "";

  // Takes the user's input and captures it
  private static String read() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      return reader.readLine();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return null;
  }

  // Determines if the user entered a valid integer or not
  private static boolean checkIfNumber(String number) {
    try {
      if (number == null || Integer.parseInt(number) <= 0) { throw new NumberFormatException(); }
    } catch (NumberFormatException ex) {
      alertError();
      return false;
    }
    return true;
  }

  // Prints out an error if an invalid number is given
  private static void alertError() {
    System.out.println("\nValue must be both an integer and greater than zero. Please try again.");
  }

  // Accepts user input, then creates and returns a new instance of the Jar class using that input
  public static Jar promptForSetup() {
    System.out.println("*********** GAME SETUP ***********\n");
    boolean validItemType = false;
    String[] responses = new String[] {"", ""};

    // Prompt the user (repeatedly if necessary) for an item
    do {
      mGuesses = 0;
      System.out.print("What type of item is stored in the jar? ");
      String itemType = Prompter.read();
      if (itemType != null && itemType.length() > 0) {
        responses[0] = itemType;
        validItemType = true;
      } else {
        System.out.println("\nPlease enter an item type.");
      }
    } while (!validItemType);

    // Prompt the user (repeatedly if necessary) for a jar capacity
    String capacityAsString;
    do {
      System.out.print("How many can fit into the jar? ");
      capacityAsString = Prompter.read();
    } while (!checkIfNumber(capacityAsString));

    responses[1] = capacityAsString;

    // Using the user's input, a new jar with the chosen number of items will be generated
    mJar = new Jar(responses[0], Integer.parseInt(responses[1]));

    return mJar;
  }

  private static void displayJarInfo() {
    System.out.println("\n**********  PLAYER AREA  **********\n");
    System.out.println("The jar can hold a maximum of " + mJar.mCapacity + " " + mJar.mItemType + ".\n");
  }

  /* Displays jar info, then accepts guesses until correct. Once correct, it will tell the user how many
     guesses were used, then add their name and score to the scoreboard. Finally, it will ask if the user would like
     play another round. If so, it will return true and start the setup process again.
     If not, all user scores will be presented on the scoreboard and the game will exit. */
  public static boolean playGame() {
    boolean gameFinished;
    displayJarInfo();

    // This will loop until the player successfully guesses the number
    do {
      if (promptForGuess()) {
        gameFinished = true;
        System.out.println("\n*******************************************");
        System.out.println("\nYou guessed correct! There are " + mJar.getActualAmount() + " " + mJar.mItemType + " in the jar.");
        System.out.println("You used " + getTotalGuesses() + " guesses.\n");
        Prompter.addScore();

        // Asks if the user would like to play another round
        boolean keepPlaying = promptForNewGame();
        if (!keepPlaying) {
          showScoreBoard();
          return false;
        }

      } else {
        gameFinished = false;
      }
    } while (!gameFinished);

    return true;
  }

  // Prompts the user for their guess
  private static boolean promptForGuess() {
    String guessAsString;
    do {
      System.out.print("Enter a guess: ");
      guessAsString = read();
    } while (!checkIfNumber(guessAsString));

    mGuesses++;
    return applyGuess(Integer.parseInt(guessAsString));
  }

  // Determines if the user's guess was too high or too low (will return false), or correct (will return true)
  private static boolean applyGuess(int guess) {
    // Checks if the guess is out of bounds; guessing too high still will use up a guess
    if (guess > mJar.mCapacity || guess < 1) {
      System.out.println("Your guess is out of bounds; the jar contains a maximum of " + mJar.mCapacity + " " + mJar.mItemType + ".");
    } else {
      if (guess > mJar.getActualAmount()) {
        System.out.println("Too high.");
      } else if (guess < mJar.getActualAmount()) {
        System.out.println("Too low.");
      }
    }
    return guess == mJar.getActualAmount();
  }

  private static int getTotalGuesses() { return mGuesses; }

  // Allows the user to add their name to the scoreboard after a round
  private static void addScore() {
    System.out.print("Enter your name: ");
    String playerName = read();
    if (playerName != null && playerName.equals("")) { playerName = "???"; }
    String scoreMessage = playerName + ": " + getTotalGuesses() + " guess(es) ";
    scoreMessage += "using a jar capable of holding " + mJar.mCapacity + " " + mJar.mItemType + ".";
    mScoreBoard += (scoreMessage + "\n");
  }

  // Prints a scoreboard formatter
  private static void showScoreBoard() {
    System.out.println("*******************************************\n");
    System.out.println("~ High Scores ~\n");
    System.out.println(mScoreBoard);
    System.out.println("*******************************************\n");
  }

  // Prompts the user to start another round of the game
  private static boolean promptForNewGame() {
    System.out.print("Want to play another round? ");
    String response = read();
    System.out.println();
    return (response != null && (response.equalsIgnoreCase("yes") ||
            response.equalsIgnoreCase("yeah") || response.equalsIgnoreCase("y")));
  }

}