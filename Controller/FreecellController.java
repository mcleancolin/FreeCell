package cs3500.hw03;

import java.io.IOException;

import java.util.List;

import java.util.Scanner;

import cs3500.hw02.PileType;

import cs3500.hw02.Cards;

import cs3500.hw02.FreecellOperations;

/**
 * Constructs the controller of the game.
 *
 */
public class FreecellController implements IFreecellController<Cards> {
  private Readable rd;
  public Appendable ap;
  private boolean sourceTypeCorrect;
  private boolean sourceNumCorrect;
  private boolean cardIndexCorrect;
  private boolean destTypeCorrect;
  private boolean destNumCorrect;
  private PileType src;
  private int srcPileNumber;
  private int cardIndex;
  private PileType dest;
  private int destPileNumber;
  private boolean gameQuit;


  /**
   * Sets the given inputs equal to their respective fields, and sets all boolean flags and
   * global variables to false, or their starting state.
   *
   * @param rd the input to the controller
   * @param ap the output of the controller
   */
  public FreecellController(Readable rd, Appendable ap) {
    if (rd == null) {
      throw new IllegalStateException("Readable cannot be initialized to null.");
    }
    if (ap == null) {
      throw new IllegalStateException("Appendable cannot be initialized to null.");
    }
    this.rd = rd;
    this.ap = ap;
    resetPiles();
    resetFlags();
  }

  /**
   * Plays a game of freecell.
   *
   * @param deck        the List of cards that should be used in the game
   * @param model       the FreecellModel that should be used in the game
   * @param numCascades the number of desired cascade piles
   * @param numOpens    the number of desired open piles
   * @param shuffle     whether or not the user wants the deck to be shuffled
   */
  public void playGame(List deck, FreecellOperations model, int numCascades,
                       int numOpens, boolean shuffle) throws IllegalStateException {
    if (this.rd == null) {
      throw new IllegalStateException("Readable cannot be initialized to null.");
    }
    if (this.ap == null) {
      throw new IllegalStateException("Appendable cannot be initialized to null.");
    }
    if (deck == null || model == null) {
      throw new IllegalArgumentException("Cannot use a null deck or model.");
    }
    Scanner sc = new Scanner(this.rd);
    if (model.isGameOver()) {
      currentGameState(model);
      try {
        this.ap.append("Game over.");
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
    else {
      try {
        model.startGame(deck, numCascades, numOpens, shuffle);
      }
      catch (IllegalArgumentException e) {
        System.out.println("Could not start game.");
      }
      while (!model.isGameOver() && !gameQuit) {
        currentGameState(model);
        nextMove(model, sc);
      }
      if (model.isGameOver()) {
        currentGameState(model);
        try {
          this.ap.append("Game over.");
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }


  /**
   * Outputs the current game state.
   *
   * @param model the FreecellModel of the game that is currently being played
   */
  private void currentGameState(FreecellOperations model) {
    try {
      this.ap.append(model.getGameState());
      this.ap.append("\n");
    }
    catch (IOException e) {
      System.out.println("Invalid model. " + e.getMessage());
    }
  }

  /**
   * Goes through the given input and ensures that all of the 5 inputs for the move method have
   * been entered, and entered in correct form.
   *
   * @param model the freecell model of the game that is being played
   * @param sc    the scanner that is being used to read the Readable input
   */
  private void nextMove(FreecellOperations model, Scanner sc) {
    resetFlags();
    resetPiles();
    while (!this.destNumCorrect && !gameQuit) {
      String s = sc.next();
      for (int i = 0; i < s.length(); i++) {
        if (!this.sourceTypeCorrect) {
          Character c = s.charAt(i);
          this.src = findPileType(c);
          if (this.src == null) {
            break;
          }
          if (this.src != null) {
            this.sourceTypeCorrect = true;
          }
        }
        else if (!this.sourceNumCorrect) {
          this.srcPileNumber = findNumber(s.substring(i)) - 1;
          if (this.srcPileNumber == -2) {
            this.sourceTypeCorrect = false;
            break;
          }
          if (this.srcPileNumber != -2) {
            this.sourceNumCorrect = true;
            break;
          }
        }
        else if (!this.cardIndexCorrect) {
          this.cardIndex = findNumber(s.substring(i)) - 1;
          if (this.cardIndex == -2) {
            break;
          }
          if (this.cardIndex != -2) {
            this.cardIndexCorrect = true;
            break;
          }
        }
        else if (!this.destTypeCorrect) {
          Character c = s.charAt(i);
          this.dest = findPileType(c);
          if (this.dest == null) {
            break;
          }
          if (this.dest != null) {
            this.destTypeCorrect = true;
          }
        }
        else if (!this.destNumCorrect) {
          this.destPileNumber = findNumber(s.substring(i)) - 1;
          if (this.destPileNumber == -2) {
            this.destTypeCorrect = false;
            break;
          }
          if (this.destPileNumber != -2) {
            this.destNumCorrect = true;
            break;
          }
        }
      }
    }
    if (gameQuit) {
      return;
    }
    try {
      model.move(this.src, this.srcPileNumber, this.cardIndex, this.dest, this.destPileNumber);
    }
    catch (IllegalArgumentException e) {
      System.out.println("Invalid move. Try again. " + e.getMessage());
    }
    catch (IndexOutOfBoundsException e) {
      System.out.println("Invalid move. Try again. " + e.getMessage());
    }
  }

  /**
   * Finds the corresponding PileType of the given Character, or tells the user to reenter if the
   * Character is not F, C, O, Q, or q.
   *
   * @param c  the Character that needs te be evaluated
   * @return   corresponding PileType of the Character
   */
  private PileType findPileType(Character c) {
    Scanner sc = new Scanner(this.rd);
    PileType src = null;
    if (Character.isDigit(c)) {
      try {
        this.ap.append("Sorry, the pile type must be either C, O, or F."
                + " Please enter one of these options.\n");
        return null;
      }
      catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
    switch (c) {
      case 'C':
        src = PileType.CASCADE;
        return src;
      case 'F':
        src = PileType.FOUNDATION;
        return src;
      case 'O':
        src = PileType.OPEN;
        return src;
      case 'q':
        try {
          this.ap.append("Game quit prematurely.");
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
        }
        this.gameQuit = true;
        break;
      case 'Q':
        try {
          this.ap.append("Game quit prematurely.");
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
        }
        this.gameQuit = true;
        break;
      default:
        try {
          this.ap.append("Sorry, the pile type must be either C, O, or F. Please enter "
                  + "one of these options.\n");
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
        }
    }
    return src;
  }

  /**
   * returns the given number that was parsed from a String in the move method, or tells the
   * user that the number is not valid and they must reenter a new one.
   *
   * @param s  the string that is being evaluated
   * @return   the int representation of the String
   */
  private int findNumber(String s) {
    Scanner sc = new Scanner(this.rd);
    for (int i = 0; i < s.length(); i++) {
      Character c = s.charAt(i);
      if (c.equals('q') || c.equals('Q')) {
        try {
          this.ap.append("Game quit prematurely.");
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
        }
        this.gameQuit = true;
        return -1;
      }
      if (!Character.isDigit(c)) {
        try {
          this.ap.append("Sorry, the pile or card number must be an integer."
                  + " Please enter a valid integer.\n");
          return -1;
        }
        catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
    return Integer.parseInt(s);
  }

  /**
   * Resets the move variable flags for a new move.
   */
  private void resetFlags() {
    this.sourceTypeCorrect = false;
    this.sourceNumCorrect = false;
    this.cardIndexCorrect = false;
    this.destTypeCorrect = false;
    this.destNumCorrect = false;
    this.gameQuit = false;
  }

  /**
   * Resets the move variables for a new move.
   */
  private void resetPiles() {
    this.srcPileNumber = -1;
    this.cardIndex = -1;
    this.destPileNumber = -1;
  }
}
