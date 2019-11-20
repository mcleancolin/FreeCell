package cs3500.hw02;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Tests for the FreecellModel class.
 */
public class FreecellModelTest {
  //ArrayList<Cards> standardDeck = new ArrayList<Cards>(new Cards(ACE, HEARTS));
  FreecellModel fm1;

  public FreecellModelTest() {
    this.fm1 = new FreecellModel();
  }

  @Test
  public void testGetDeck1() {
    assertEquals(this.fm1.getDeck(), fm1.getDeck());
  }

  @Test
  public void testStartGame1() {
    assertEquals(0, this.fm1.getOpen().size());
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    assertEquals(4, this.fm1.getOpen().size());
  }

  @Test
  public void testStartGame2() {
    assertEquals(0, this.fm1.getCascade().size());
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    assertEquals(13, this.fm1.getCascade().get(0).size());
  }

  // should throw exception because the deck is invalid
  @Test(expected = IllegalArgumentException.class)
  public void testStartGame3() throws Exception {
    ArrayList<Cards> loc = new ArrayList<Cards>();
    loc.add(new Cards(Rank.EIGHT, Suit.HEARTS));
    this.fm1.startGame(loc, 4, 4, false);
  }

  // should throw exception because the the card index is not the last index
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput1() {
    this.fm1.move(PileType.OPEN, 2, 1, PileType.OPEN, 1);
  }

  // should throw exception because the pile number is higher than the number of open piles
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput2() {
    this.fm1.move(PileType.OPEN, 7, 0, PileType.OPEN, 1);
  }

  // should throw exception because the card index is negative
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput3() {
    this.fm1.move(PileType.OPEN, 2, -3, PileType.OPEN, 1);
  }

  // should throw exception because the pile number is negative
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput4() {
    this.fm1.move(PileType.OPEN, -2, 0, PileType.OPEN, 1);
  }

  // should throw exception because you cannot move cards in the foundation pile
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput5() {
    this.fm1.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 1);
  }

  // should throw exception because the card index is not the lowest in the cascade pile
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput6() {
    this.fm1.move(PileType.CASCADE, 7, 0, PileType.OPEN, 1);
  }

  // should throw exception because the pile number is higher than the number of cascade piles
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput7() {
    this.fm1.move(PileType.CASCADE, 15, 2, PileType.OPEN, 1);
  }

  // should throw exception because the card index is higher than the number of cascade piles
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput8() {
    this.fm1.move(PileType.CASCADE, 1, 22, PileType.OPEN, 1);
  }

  // should throw exception because the destination pile is greater than the number of open piles
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput9() {
    this.fm1.move(PileType.CASCADE, 2, 2, PileType.OPEN, 7);
  }

  // should throw exception because the foundation pile 4 does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput10() {
    this.fm1.move(PileType.CASCADE, 2, 2, PileType.FOUNDATION, 4);
  }

  // should throw exception because the cascade pile 23 does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput11() {
    this.fm1.move(PileType.CASCADE, 2, 2, PileType.CASCADE, 23);
  }

  // should throw exception because there is more than one card trying to be moved
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidFMove1() {
    this.fm1.startGame(this.fm1.getDeck(),8, 4, false );
    ArrayList<Cards> loc1 = new ArrayList<>();
    loc1.add(new Cards(Rank.ACE, Suit.HEARTS));
    loc1.add(new Cards(Rank.EIGHT, Suit.CLUBS));
    this.fm1.move(PileType.CASCADE, 1, 6, PileType.FOUNDATION, 1);
  }

  // should throw exception because the first card in the foundation pile is not an ace
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidFMove2() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    this.fm1.move(PileType.CASCADE, 2, 6, PileType.OPEN, 1);
    this.fm1.move(PileType.OPEN, 1, 0, PileType.FOUNDATION, 3);
  }

  // should throw exception because there is more than one card trying to be added to an open pile
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidOMove1() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    this.fm1.move(PileType.CASCADE, 2, 6, PileType.OPEN, 1);
    this.fm1.move(PileType.CASCADE, 2, 5, PileType.OPEN, 1);
  }

  // should throw exception because  the suits of the cards are both red in the cascade pile
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidCMove1() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    this.fm1.move(PileType.CASCADE, 1, 12, PileType.OPEN, 1);
    this.fm1.move(PileType.CASCADE, 1, 11, PileType.OPEN, 2);
    this.fm1.move(PileType.CASCADE, 1, 10, PileType.OPEN, 3);
    this.fm1.move(PileType.CASCADE, 1, 9, PileType.CASCADE, 2);
  }

  // should throw exception because both of the cards are black in this cascade pile
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidCMove2() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    this.fm1.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 2);
  }

  // should throw exception because the ordinals are not off by one and the suit is different
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidCMove3() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    this.fm1.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 2);
  }

  // should throw exception because is the ordinal is higher than accepted
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidCMove5() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    this.fm1.move(PileType.CASCADE, 2, 12, PileType.CASCADE, 1);

  }

  @Test
  public void testMove1() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    assertEquals(0, this.fm1.getFoundation().get(0).size());
    ArrayList<Cards> newOpen = new ArrayList<Cards>();
    newOpen.add(new Cards(Rank.ACE, Suit.HEARTS));
    this.fm1.setOpen(0, newOpen);
    this.fm1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(1, this.fm1.getFoundation().get(0).size());
  }

  @Test
  public void testMove2() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    assertEquals(0, this.fm1.getFoundation().get(0).size());
    ArrayList<Cards> loc1 = new ArrayList<>();
    loc1.add(new Cards(Rank.ACE, Suit.CLUBS));
    loc1.add(new Cards(Rank.TWO, Suit.CLUBS));
    this.fm1.setFoundation1(0, loc1);
    assertEquals(2, this.fm1.getFoundation().get(0).size());
    ArrayList<Cards> newOpen = new ArrayList<Cards>();
    newOpen.add(new Cards(Rank.THREE, Suit.CLUBS));
    this.fm1.setOpen(0, newOpen);
    this.fm1.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(3, this.fm1.getFoundation().get(0).size());
  }

  @Test
  public void testMove3() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    assertEquals(0, this.fm1.getOpen().get(2).size());
    ArrayList<Cards> loc1 = new ArrayList<>();
    loc1.add(new Cards(Rank.TWO, Suit.CLUBS));
    this.fm1.setOpen(0, loc1);
    assertEquals(1, this.fm1.getOpen().get(0).size());
    this.fm1.move(PileType.OPEN, 0, 0, PileType.OPEN, 2);
    assertEquals(1, this.fm1.getOpen().get(2).size());
  }

  @Test
  public void testMove4() throws Exception {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    assertEquals(0, this.fm1.getFoundation().get(0).size());
    ArrayList<Cards> newCascade = new ArrayList<Cards>();
    newCascade.add(new Cards(Rank.ACE, Suit.HEARTS));
    this.fm1.updateCascade(newCascade);
    this.fm1.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 0);
    assertEquals(1, this.fm1.getFoundation().get(0).size());
  }

  @Test
  public void testMove5() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    ArrayList<Cards> newCascade1 = new ArrayList<Cards>();
    newCascade1.add(new Cards(Rank.FOUR, Suit.HEARTS));
    this.fm1.updateCascade(newCascade1);
    assertEquals(1, this.fm1.getCascade().get(4).size());
    ArrayList<Cards> newCascade2 = new ArrayList<Cards>();
    newCascade2.add(new Cards(Rank.THREE, Suit.CLUBS));
    this.fm1.updateCascade(newCascade2);
    this.fm1.move(PileType.CASCADE, 5, 0, PileType.CASCADE, 4);
    assertEquals(2, this.fm1.getCascade().get(4).size());
  }

  @Test
  public void testMove6() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    ArrayList<Cards> newCascade1 = new ArrayList<Cards>();
    newCascade1.add(new Cards(Rank.FOUR, Suit.HEARTS));
    this.fm1.updateCascade(newCascade1);
    assertEquals(0, this.fm1.getOpen().get(0).size());
    this.fm1.move(PileType.CASCADE, 4, 0, PileType.OPEN, 0);
    assertEquals(1, this.fm1.getOpen().get(0).size());
    assertEquals(0, this.fm1.getCascade().get(4).size());
  }

  @Test
  public void testMove7() {
    this.fm1.startGame(this.fm1.getDeck(), 6, 4, false);
    assertEquals(0, this.fm1.getFoundation().get(3).size());
    ArrayList<Cards> loc1 = new ArrayList<>();
    loc1.add(new Cards(Rank.ACE, Suit.CLUBS));
    loc1.add(new Cards(Rank.TWO, Suit.CLUBS));
    this.fm1.setFoundation1(3, loc1);
    assertEquals(2, this.fm1.getFoundation().get(3).size());
    ArrayList<Cards> newOpen = new ArrayList<Cards>();
    newOpen.add(new Cards(Rank.THREE, Suit.CLUBS));
    this.fm1.setOpen(3, newOpen);
    this.fm1.move(PileType.OPEN, 3, 0, PileType.FOUNDATION, 3);
    assertEquals(3, this.fm1.getFoundation().get(3).size());
    assertEquals(0, this.fm1.getOpen().get(3).size());
  }

  @Test
  public void testMove8() {
    this.fm1.startGame(this.fm1.getDeck(), 6, 4, false);
    assertEquals(0, this.fm1.getFoundation().get(3).size());
    ArrayList<Cards> newOpen = new ArrayList<Cards>();
    newOpen.add(new Cards(Rank.ACE, Suit.CLUBS));
    this.fm1.setOpen(3, newOpen);
    this.fm1.move(PileType.OPEN, 3, 0, PileType.FOUNDATION, 3);
    assertEquals(1, this.fm1.getFoundation().get(3).size());
    assertEquals(0, this.fm1.getOpen().get(3).size());
  }

  @Test
  public void testGetGameState1() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n" +
            "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
            "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n" +
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣", fm1.getGameState());
  }

  @Test
  public void testGetGameState2() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    this.fm1.move(PileType.CASCADE, 2, 12, PileType.OPEN, 1);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2: Q♣\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n" +
            "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
            "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣\n" +
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣", fm1.getGameState());
  }

  @Test
  public void testIsGameOver1() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    assertEquals(false, this.fm1.isGameOver());
  }

  @Test
  public void testIsGameOver2() {
    this.fm1.startGame(this.fm1.getDeck(), 4, 4, false);
    int count = 0;
    for (int i = 0; i < this.fm1.getDeck().size(); i++) {
      if (i % 13 == 0) {
        count++;
      }
      this.fm1.setFoundation2(count - 1, this.fm1.getDeck().get(i));
    }
    assertEquals(true, this.fm1.isGameOver());
  }
}