package cs3500.hw02;

import java.util.ArrayList;

import java.util.List;

public class FreecellModel implements FreecellOperations<Cards> {
  protected List<Cards> deck;
  protected ArrayList<ArrayList<Cards>> cascadePiles;
  protected ArrayList<ArrayList<Cards>> foundationPiles;
  protected ArrayList<ArrayList<Cards>> openPiles;
  protected boolean gameStarted;

  /**
   *Initializes all of the fields of this FreecellModel. The constructor initalizes everything to
   * an empty list, except the deck. The deck is created as a standard, unshuffled deck. The
   * gameStarted field is false.
   */
  public FreecellModel() {
    this.deck = generateNewDeck();
    this.gameStarted = false;
    this.cascadePiles = new ArrayList<ArrayList<Cards>>();
    this.openPiles = new ArrayList<ArrayList<Cards>>();
    this.foundationPiles = new ArrayList<ArrayList<Cards>>();
  }

  // returns the cascade piles in this freecell model
  public void updateCascade(ArrayList<Cards> newCascade) {
    this.cascadePiles.add(newCascade);
  }

  // sets the open piles in this freecell model
  protected void setOpen(int i, ArrayList<Cards> newOpen) {
    this.openPiles.set(i, newOpen);
  }

  // sets the cascade piles in this freecell model
  public void setFoundation1(int i, ArrayList<Cards> newFoundation) {
    this.foundationPiles.set(i, newFoundation);
  }

  // returns the cascade piles in this freecell model
  public void setFoundation2(int i, Object c) {
    this.foundationPiles.get(i).add((Cards) c);
  }

  // returns the cascade piles in this freecell model
  public ArrayList<ArrayList<Cards>> getCascade() {
    return this.cascadePiles;
  }

  // returns the foundation piles in this freecell model
  public ArrayList<ArrayList<Cards>> getFoundation() {
    return this.foundationPiles;
  }

  // returns the open piles in this freecell model
  public ArrayList<ArrayList<Cards>> getOpen() {
    return this.openPiles;
  }

  // returns the deck we are using in this game of freecell
  public List getDeck() {
    return this.deck;
  }

  /**
   * Creates an ArrayList of Cards that is a valid deck. It uses the Suit and Rank enums to ensure
   * that every combination of Rank and Suit is represented in the deck. The for each loop ensures
   * that there is only one of every Cards in the deck.
   *
   * @return a valid list of cards
   */
  private ArrayList<Cards> generateNewDeck() {
    ArrayList<Cards> loc = new ArrayList<Cards>();
    for (Suit s : Suit.values()) {
      for (Rank r : Rank.values()) {
        loc.add(new Cards(r, s));
      }
    }
    return loc;
  }

  /**
   * Initializes a new game of Freecell. It makes sure that all of the input is according to the
   * rules, such as more than one open deck, but allows for infinitely many cascade/open piles.
   * If the shuffle field is true, then the List of Cards gets shuffled using the built in shuffle
   * method. Lastly, it deals all of the cards into the cascade piles in a round robin order.
   *
   * @param deck            the deck to be dealt
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the input is invalid
   */
  public void startGame(List deck, int numCascadePiles, int numOpenPiles,
                        boolean shuffle) throws IllegalArgumentException {
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("There needs to be more cascade piles.");
    }
    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("There needs to be more open piles.");
    }
    checkValidDeck(deck);
    if (gameStarted) {
      this.deck = generateNewDeck();
      this.gameStarted = false;
      this.cascadePiles = new ArrayList<ArrayList<Cards>>();
      this.openPiles = new ArrayList<ArrayList<Cards>>();
      this.foundationPiles = new ArrayList<ArrayList<Cards>>();
    }
    this.deck = deck;
    if (shuffle) {
      java.util.Collections.shuffle(this.deck);
    }
    for (int i = 0; i < numCascadePiles; i++) {
      this.cascadePiles.add(i, new ArrayList<Cards>());
    }
    for (int i = 0; i < numOpenPiles; i++) {
      this.openPiles.add(i, new ArrayList<Cards>());
    }
    for (int i = 0; i < 4; i++) {
      this.foundationPiles.add(i, new ArrayList<Cards>());
    }
    for (int i = 0; i < this.deck.size(); i++) {
      int pileNum = i % numCascadePiles;
      this.cascadePiles.get(pileNum).add(this.deck.get(i));
    }
    gameStarted = true;
  }

  /**
   * Checks to see if the given list of cards is a valid deck.
   *
   * @param deck the list of cards in the deck
   * @throws IllegalArgumentException if the deck is not properly formed
   */
  private void checkValidDeck(List deck) throws IllegalArgumentException {
    ArrayList<Cards> validDeck = generateNewDeck();
    for (Object o : deck) {
      if (validDeck.contains(o)) {
        validDeck.remove(o);
      }
      else {
        throw new IllegalArgumentException("This is not a valid deck.");
      }
    }
    if (validDeck.size() != 0) {
      throw new IllegalArgumentException("This is not a valid deck.");
    }
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
    if (addedCards.size() > 1) {
      throw new IllegalArgumentException();
    }
    findMoveType(destination, addedCards, destPileNumber);
    for (int i = cardIndex; i < sourcePile.get(pileNumber).size(); i++) {
      destPile.get(destPileNumber).add(sourcePile.get(pileNumber).get(i));
    }
    sourcePile.get(pileNumber).remove(cardIndex);
  }

  // decides which type of move is being made
  protected void findMoveType(PileType destination, ArrayList<Cards> addedCards,
                              int destPileNumber) {
    switch (destination) {
      case FOUNDATION :
        checkValidFMove(addedCards, destPileNumber);
        break;
      case OPEN :
        checkValidOMove(addedCards, destPileNumber);
        break;
      case CASCADE :
        checkValidCMove(addedCards, destPileNumber);
        break;
      default :
        System.out.println("Invalid pile type");
    }
  }

  /**
   * Checks to see if the given input to the move method is valid.
   *
   * @param source          the PileType of the pile the card is being taken from
   * @param pileNumber      the number of the pile the user is taking the card from
   * @param cardIndex       the card in the pile which the user is taking the card from
   * @param destPile        the pile that the user is trying to move the card to
   * @param destPileNumber  the pile number of the pile which is being added to
   * @throws IllegalArgumentException if the input is invalid
   */
  protected void checkValidInput(PileType source, int pileNumber, int cardIndex,
                                 ArrayList<ArrayList<Cards>> destPile, int destPileNumber)
          throws IllegalArgumentException {
    if (cardIndex < 0 || pileNumber < 0) {
      throw new IllegalArgumentException("Cannot move from a negative index.");
    }
    if (source.equals(PileType.FOUNDATION)) {
      throw new IllegalArgumentException("Cannot move from the foundation pile.");
    }
    if (source.equals(PileType.OPEN) && ((cardIndex > 0)
            || (pileNumber > this.openPiles.size()))) {
      throw new IllegalArgumentException("This is an invalid move from an open pile.");
    }
    if (source.equals(PileType.CASCADE) && (pileNumber > this.cascadePiles.size())
            || (cardIndex > this.cascadePiles.get(pileNumber).size())) {
      throw new IllegalArgumentException("This is an invalid move from a cascade pile.");
    }
    if (destPileNumber > destPile.size()) {
      throw new IllegalArgumentException("You cannot move a card to this index.");
    }
  }

  /**
   * Checks to see if the move to a foundation pile, from any type of pile, is valid. It utilizes
   * the rules of the game to make sure that the foundation piles maintain integrity.
   *
   * @param addedCards                 the card(s) that are being added to another pile
   * @param destPileNumber             the pile the card(s) are being moved to
   * @throws IllegalArgumentException  if the move is invalid
   */
  protected void checkValidFMove(ArrayList<Cards> addedCards, int destPileNumber)
          throws IllegalArgumentException {
    if (addedCards.size() > 1) {
      throw new IllegalArgumentException();
    }
    if (this.foundationPiles.get(destPileNumber).size() == 0
            && !(addedCards.get(0).rank.equals(Rank.ACE))) {
      throw new IllegalArgumentException("The first card in the pile must be an ace");
    }
    if (matchingFSuit(destPileNumber, addedCards)) {
      throw new IllegalArgumentException("The suits of the cards do not match.");
    }
    if (this.foundationPiles.get(destPileNumber).size() == 0
            && addedCards.get(0).rank.equals(Rank.ACE)) {
      return;
    }
    else if (findLast(this.foundationPiles, destPileNumber).rank.ordinal()
            != addedCards.get(0).rank.ordinal() - 1) {
      throw new IllegalArgumentException("The card needs to be one rank higher than the card"
              + " that is currently in the foundation pile.");
    }
  }

  /**
   * Checks to see if the move to an open pile, from any type of pile, is valid. It utilizes
   * the rules of the game to make sure that the open piles maintain integrity.
   *
   * @param addedCards                 the card(s) that are being added to another pile
   * @param destPileNumber             the pile the card(s) are being moved to
   * @throws IllegalArgumentException  if the move is invalid
   */
  protected void checkValidOMove(ArrayList<Cards> addedCards, int destPileNumber)
          throws IllegalArgumentException {
    if (addedCards.size() > 1) {
      throw new IllegalArgumentException();
    }
    if (this.openPiles.get(destPileNumber).size() > 0) {
      throw new IllegalArgumentException("This open pile does not exist.");
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
    this.checkSuits(firstSource.suit, receiver.suit);
    if (firstSource.rank.ordinal() + 1 != receiver.rank.ordinal()) {
      throw new IllegalArgumentException("You cannot stack cards that are higher than the "
              + "current card in this pile.");
    }
  }

  /**
   * Finds the cards that the user is trying to move.
   *
   * @param cardIndex  the first card in the pile that the user is trying to move
   * @param pileNumber the number of the pile tha the user is trying to move
   * @param sourcePile the type of pile the user is trying to take cards from
   * @return           the list of cards that the user is trying to move
   */
  protected ArrayList<Cards> findAddedCards(int cardIndex, int pileNumber,
                                            ArrayList<ArrayList<Cards>> sourcePile) {
    ArrayList<Cards> aoc = new ArrayList<Cards>();
    for (int i = cardIndex; i < sourcePile.get(pileNumber).size(); i++) {
      aoc.add(sourcePile.get(pileNumber).get(i));
    }
    return aoc;
  }

  /**
   * Checks to see if the suits of the cards that are being added to each other match. If they are
   * equal, then it returns false, because it should not throw an exception.
   *
   * @param destPileNumber the pile that is being added to
   * @param addedCards     the card(s) that the user is trying to move
   * @return               true if the suits are the same
   */
  private boolean matchingFSuit(int destPileNumber, ArrayList<Cards> addedCards) {
    if (this.foundationPiles.get(destPileNumber).size() == 0) {
      return false;
    }
    else {
      return !findLast(this.foundationPiles, destPileNumber).suit.equals(addedCards.get(0).suit);
    }
  }

  /**
   * Finds the last card in a pile. This is used when the user is trying to move for a specific
   * pile, and the suit and rank of the card in that pile must be known in order to decide if the
   * move is valid or not.
   *
   * @param destinationPile the list of piles that the user is moving the card to
   * @param destPileNumber  the specific pile that the user is moving the card to
   * @return                the card that the user is trying to add on top of
   */
  protected Cards findLast(ArrayList<ArrayList<Cards>> destinationPile, int destPileNumber) {
    if (destinationPile.get(destPileNumber).size() == 0) {
      return destinationPile.get(destPileNumber).get(0);
    }
    int lastIndex = destinationPile.get(destPileNumber).size() - 1;
    return destinationPile.get(destPileNumber).get(lastIndex);
  }

  /**
   * Finds the corresponding pile list of the PileType that the user is moving to.
   *
   * @param source the PileType the user is moving the cards to
   * @return       the list of Piles the user is moving from
   */
  protected ArrayList<ArrayList<Cards>> findPile(PileType source) {
    switch (source) {
      case FOUNDATION :
        return this.foundationPiles;
      case OPEN :
        return this.openPiles;
      case CASCADE :
        return this.cascadePiles;
      default :
        System.out.println("Invalid pile type");
    }
    return new ArrayList<ArrayList<Cards>>();
  }

  /**
   * Checks to see whether the game is over or not. If the foundation piles have 52 cards in total,
   * then you know that all of the cards have been placed into the foundation piles, and the game
   * is over.
   *
   * @return true if the game is over
   */
  public boolean isGameOver() {
    int count = 0;
    for (int i = 0; i < this.foundationPiles.size(); i++) {
      for (int j = 0; j < this.foundationPiles.get(i).size(); j++) {
        count++;
      }
    }
    return count == 52;
  }


  /**
   * Produces a visual representation of the current state of the game. It first shows the
   * foundation piles, and all of the cards that are in them. It then shoes the open piles, and
   * then the cascade piles. If there are no cards in the piles, then it goes to the next pile. If
   * there are cards, then they are all separated by a comma, but there is no comma after the last
   * card. The last cascade pile does not have a new line inserted after it.
   *
   * @return the string representation of the game
   */
  public String getGameState() {
    if (!gameStarted) {
      return "";
    }
    String gameState = "";
    for (int i = 0; i < this.foundationPiles.size(); i++) {
      if (0 ==  this.foundationPiles.get(i).size()) {
        gameState += "F" + (i + 1) + ":";
      }
      else {
        gameState += "F" + (i + 1) + ": ";
      }
      for (int j = 0; j < this.foundationPiles.get(i).size(); j++) {
        if (j == this.foundationPiles.get(i).size() - 1) {
          gameState += this.foundationPiles.get(i).get(j).toString();
        }
        else {
          gameState += this.foundationPiles.get(i).get(j).toString() + ", ";
        }
      }
      gameState += "\n";
    }
    for (int i = 0; i < this.openPiles.size(); i++) {
      if (0 ==  this.openPiles.get(i).size()) {
        gameState += "O" + (i + 1) + ":";
      }
      else {
        gameState += "O" + (i + 1) + ": ";
      }
      for (int j = 0; j < this.openPiles.get(i).size(); j++) {
        if (j == this.openPiles.get(i).size() - 1) {
          gameState += this.openPiles.get(i).get(j).toString();
        }
        else {
          gameState += this.openPiles.get(i).get(j).toString() + ", ";
        }
      }
      gameState += "\n";
    }
    for (int i = 0; i < this.cascadePiles.size(); i++) {
      if (0 ==  this.cascadePiles.get(i).size()) {
        gameState += "C" + (i + 1) + ":";
      }
      else {
        gameState += "C" + (i + 1) + ": ";
      }
      for (int j = 0; j < this.cascadePiles.get(i).size(); j++) {
        if (j == this.cascadePiles.get(i).size() - 1) {
          gameState += this.cascadePiles.get(i).get(j).toString();
        }
        else {
          gameState += this.cascadePiles.get(i).get(j).toString() + ", ";
        }
      }
      if (i == this.cascadePiles.size() - 1) {
        return gameState;
      }
      else {
        gameState += "\n";
      }
    }
    return gameState;
  }

  /**
   * Checks to see if the suits of two cards are opposite colors.
   *
   * @param s1 the suit of the top card being checked
   * @param s2 the suit of the bottom bard being checked
   */
  // ensures that the list of cards has alternating colors
  protected void checkSuits(Suit s1, Suit s2) throws IllegalArgumentException {
    switch (s1) {
      case HEARTS :
        if (s2.equals(Suit.HEARTS) || s2.equals(Suit.DIAMONDS)) {
          throw new IllegalArgumentException("Suits do not match.");
        }
        break;
      case DIAMONDS :
        if (s2.equals(Suit.HEARTS) || s2.equals(Suit.DIAMONDS)) {
          throw new IllegalArgumentException("Suits do not match.");
        }
        break;
      case CLUBS :
        if (s2.equals(Suit.CLUBS) || s2.equals(Suit.SPADES)) {
          throw new IllegalArgumentException("Suits do not match.");
        }
        break;
      case SPADES :
        if (s2.equals(Suit.CLUBS) || s2.equals(Suit.SPADES)) {
          throw new IllegalArgumentException("Suits do not match." );
        }
        break;
      default :
        break;
    }
  }
}
