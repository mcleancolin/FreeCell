package cs3500.hw02;

public class Cards {
  public Suit suit;
  public Rank rank;

  Cards(Rank rank, Suit suit) {
    this.suit = suit;
    this.rank = rank;
  }

  /**
   * Converts each enum type to a string representation of it.
   *
   * @return the string version of the enum
   */
  // returns the value of this card
  public String toString() {
    String name = "";
    switch (rank) {
      case ACE:
        name += "A";
        break;
      case TWO:
        name += "2";
        break;
      case THREE:
        name += "3";
        break;
      case FOUR:
        name += "4";
        break;
      case FIVE:
        name += "5";
        break;
      case SIX:
        name += "6";
        break;
      case SEVEN:
        name += "7";
        break;
      case EIGHT:
        name += "8";
        break;
      case NINE:
        name += "9";
        break;
      case TEN:
        name += "10";
        break;
      case JACK:
        name += "J";
        break;
      case QUEEN:
        name += "Q";
        break;
      case KING:
        name += "K";
        break;
      default:
        System.out.println("Invalid rank type");
    }
    switch (suit) {
      case HEARTS:
        name += "♥";
        break;
      case DIAMONDS:
        name += "♦";
        break;
      case SPADES :
        name += "♠";
        break;
      case CLUBS :
        name += "♣";
        break;
      default :
        System.out.println("Invalid suit type");
    }
    return name;
  }

  // checks to see if the two cards are equals
  public boolean equals(Object other) {
    return ((other instanceof Cards) && ((Cards) other).rank == this.rank
            && ((Cards) other).suit == this.suit);
  }

  // creates a hashcode for this card
  public int hashCode() {
    return this.rank.ordinal() * 3881;
  }
}
