package cs3500.hw04;

import java.util.ArrayList;

import cs3500.hw02.Cards;

import cs3500.hw02.FreecellModel;

import cs3500.hw02.PileType;

/**
 * Creates a version of Freecell that has the same functionality as FreecellModel, but it allows
 * the user to move multiple cards from one cascade pile to another. The FreecellModelMultiple
 * uses all the same methods as the FreecellModel, but overrides the move method and the
 * checkValidCMove method, the method that controls the cascade move.
 */
public class FreecellModelMultiple extends FreecellModel {
  /**
   * Initializes all of the fields of this FreecellModelMultiple. The constructor initializes
   * everything to an empty list, except the deck. The deck is created as a standard, not shuffled
   * deck. The gameStarted field is false.
   */
  public FreecellModelMultiple() {
    super();
  }

  /**
   * Checks to see if the given move is valid. If the move is valid, then it removes the card from
   * the source pile and proceeds to put it in the destination pile. If the move is not valid, then
   * it throws an IllegalArgumentException.
   *
   * @param source         the type of the source pile
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is invalid
   */
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException {
    ArrayList<ArrayList<Cards>> sourcePile = findPile(source);
    ArrayList<ArrayList<Cards>> destPile = findPile(destination);
    checkValidInput(source, pileNumber, cardIndex, destPile, destPileNumber);
    ArrayList<Cards> addedCards = findAddedCards(cardIndex, pileNumber, sourcePile);
    findMoveType(destination, addedCards, destPileNumber);
    for (int i = cardIndex; i < sourcePile.get(pileNumber).size(); i++) {
      destPile.get(destPileNumber).add(sourcePile.get(pileNumber).get(i));
    }
    for (int i = cardIndex; i < sourcePile.get(pileNumber).size(); i++) {
      sourcePile.get(pileNumber).remove(i);
      i--;
    }
  }

  /**
   * Checks to see if the move to a Cascade pile, from any type of pile, is valid. It utilizes
   * the rules of the game to make sure that the cascade piles maintain integrity.
   *
   * @param addedCards                 the card(s) that are being added to another pile
   * @param destPileNumber             the pile the card(s) are being moved to
   * @throws IllegalArgumentException  if the move is invalid
   */
  protected void checkValidCMove(ArrayList<Cards> addedCards, int destPileNumber)
          throws IllegalArgumentException {
    if (this.cascadePiles.get(destPileNumber).size() == 0) {
      return;
    }
    Cards receiver = findLast(this.cascadePiles, destPileNumber);
    Cards firstSource = addedCards.get(0);
    if (addedCards.size() > 1) {
      checkOrdering(addedCards);
      checkNumOpenSpaces(addedCards);
    }
    checkSuits(firstSource.suit, receiver.suit);

    if (firstSource.rank.ordinal() + 1 != receiver.rank.ordinal()) {
      throw new IllegalArgumentException("You cannot stack cards that are higher than the " +
              "current card in this pile.");
    }

  }

  /**
   * ensures that the multiple cards being moved are ordered correctly.
   *
   * @param addedCards                the list of cards that is attempting to be moved
   * @throws IllegalArgumentException if the list of cards are not ordered correctly
   */
  private void checkOrdering(ArrayList<Cards> addedCards) throws IllegalArgumentException {
    for (int i = 0; i < addedCards.size() - 1; i++) {
      if (addedCards.get(i).rank.ordinal() != addedCards.get(i + 1).rank.ordinal() + 1) {
        throw new IllegalArgumentException("The pile of cards you tried to move are not properly" +
                " ordered according to their ranks.");
      }
      checkSuits(addedCards.get(i).suit, addedCards.get(i + 1).suit);
    }
  }

  /**
   * checks the number of open spaces in this game.
   *
   * @param addedCards                the list of cards that is attempting to be moved
   * @throws IllegalArgumentException if the number of open spaces is too low
   */
  private void checkNumOpenSpaces(ArrayList<Cards> addedCards) throws IllegalArgumentException {
    int n = 0;
    int k = 0;
    for (int i = 0; i < this.cascadePiles.size(); i++) {
      if (this.cascadePiles.get(i).size() == 0) {
        k++;
      }
    }
    for (int i = 0; i < this.openPiles.size(); i++) {
      if (this.openPiles.get(i).size() == 0) {
        n++;
      }
    }
    if (((n + 1) * Math.pow(2, k)) < addedCards.size()) {
      throw new IllegalArgumentException("It is not possible to move this many cards at once.");
    }
  }
}
