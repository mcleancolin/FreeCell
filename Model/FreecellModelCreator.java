package cs3500.hw04;

import cs3500.hw02.Cards;
import cs3500.hw02.FreecellModel;
import cs3500.hw02.FreecellOperations;

public class FreecellModelCreator {


  /**
   * Defines the two types of possible games the user can play.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * Creates a new model for the desired type of game that the user wants to play.
   *
   * @param type the type of game the user wants to play
   * @return     the model of the corresponding game type
   */
  public static FreecellOperations<Cards> create(GameType type) {
    switch (type) {
      case SINGLEMOVE :
        return new FreecellModel();
      case MULTIMOVE :
        return new FreecellModelMultiple();
      default :
        System.out.println("Sorry you must pick another game type.");
    }
    return new FreecellModel();
  }
}
