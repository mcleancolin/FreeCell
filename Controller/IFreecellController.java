package cs3500.hw03;

import cs3500.hw02.FreecellOperations;

import java.util.List;

public interface IFreecellController<K> {

  /**
   *  This method should start a new game of Freecell using the provided model, number of cascade
   *  and open piles and the provided deck. If "shuffle" is set to false, the deck must be used
   *  as-is, else the deck should be shuffled. It should throw an IllegalStateException only if the
   *  controller has not been initialized properly to receive input and transmit output.
   *
   * @param deck         the List of cards that should be used in the game
   * @param model        the FreecellModel that should be used in the game
   * @param numCascades  the number of desired cascade piles
   * @param numOpens     the number of desired open piles
   * @param shuffle      whether or not the user wants the deck to be shuffled
   */
  void playGame(List<K> deck, FreecellOperations<K> model, int numCascades,
                int numOpens, boolean shuffle) throws IllegalStateException;
}
