package cs3500.hw04;

import org.junit.Test;

import cs3500.hw02.Cards;

import cs3500.hw02.FreecellOperations;

import cs3500.hw02.PileType;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the FreecellModelMultiple class.
 */
public class FreecellModelMultipleTest {
  FreecellOperations<Cards> fcm1;

  public FreecellModelMultipleTest() {
    this.fcm1 = FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
  }

  @Test
  public void testStartGame1() {
    this.fcm1.startGame(this.fcm1.getDeck(), 8, 4, false);
    assertEquals(52, this.fcm1.getDeck().size());
  }

  @Test
  public void testMultipleMove1() {
    this.fcm1.startGame(this.fcm1.getDeck(), 8, 4, false);
    this.fcm1.move(PileType.CASCADE, 2, 6, PileType.OPEN, 1);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2: Q♣\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 9♥, 4♦, Q♦, 7♠, 2♣, 10♣\n" +
            "C2: 2♥, 10♥, 5♦, K♦, 8♠, 3♣, J♣\n" +
            "C3: 3♥, J♥, 6♦, A♠, 9♠, 4♣\n" +
            "C4: 4♥, Q♥, 7♦, 2♠, 10♠, 5♣, K♣\n" +
            "C5: 5♥, K♥, 8♦, 3♠, J♠, 6♣\n" +
            "C6: 6♥, A♦, 9♦, 4♠, Q♠, 7♣\n" +
            "C7: 7♥, 2♦, 10♦, 5♠, K♠, 8♣\n" +
            "C8: 8♥, 3♦, J♦, 6♠, A♣, 9♣", this.fcm1.getGameState());
  }

  @Test
  public void testMultipleMove2() {
    this.fcm1.startGame(this.fcm1.getDeck(), 8, 4, false);
    this.fcm1.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    this.fcm1.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
    this.fcm1.move(PileType.CASCADE, 0, 4, PileType.OPEN, 2);
    this.fcm1.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 0);
    this.fcm1.move(PileType.CASCADE, 0, 3, PileType.CASCADE, 3);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 10♣\n" +
            "O2: 2♣\n" +
            "O3: 7♠\n" +
            "O4:\n" +
            "C1: A♥, 9♥, 4♦, J♣\n" +
            "C2: 2♥, 10♥, 5♦, K♦, 8♠, 3♣\n" +
            "C3: 3♥, J♥, 6♦, A♠, 9♠, 4♣, Q♣\n" +
            "C4: 4♥, Q♥, 7♦, 2♠, 10♠, 5♣, K♣, Q♦, J♣\n" +
            "C5: 5♥, K♥, 8♦, 3♠, J♠, 6♣\n" +
            "C6: 6♥, A♦, 9♦, 4♠, Q♠, 7♣\n" +
            "C7: 7♥, 2♦, 10♦, 5♠, K♠, 8♣\n" +
            "C8: 8♥, 3♦, J♦, 6♠, A♣, 9♣", this.fcm1.getGameState());
  }

  // should throw exception because the cards being moved are not in order
  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultipleInvalid1() throws Exception {
    this.fcm1.startGame(this.fcm1.getDeck(), 8, 4, false);
    this.fcm1.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    this.fcm1.move(PileType.CASCADE, 0, 3, PileType.CASCADE, 3);
  }

  // should throw exception because receiving card is not one rank lower
  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultipleInvalid2() throws Exception {
    this.fcm1.startGame(this.fcm1.getDeck(), 8, 4, false);
    this.fcm1.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    this.fcm1.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
    this.fcm1.move(PileType.CASCADE, 0, 4, PileType.OPEN, 2);
    this.fcm1.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 0);
    this.fcm1.move(PileType.CASCADE, 0, 3, PileType.CASCADE, 4);
  }

  // should throw exception because receiving card is not the opposite color
  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultipleInvalid3() throws Exception {
    this.fcm1.startGame(this.fcm1.getDeck(), 28, 4, false);
    this.fcm1.move(PileType.CASCADE, 22, 1, PileType.CASCADE, 25);
    this.fcm1.move(PileType.CASCADE, 19, 1, PileType.CASCADE, 22);
    this.fcm1.move(PileType.CASCADE, 23, 1, PileType.OPEN, 1);
    this.fcm1.move(PileType.CASCADE, 22, 0, PileType.CASCADE, 23);
  }

  // should throw exception because source cards are not ordered correctly
  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultipleInvalid4() throws Exception {
    this.fcm1.startGame(this.fcm1.getDeck(), 28, 4, false);
    this.fcm1.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 25);
  }

  // should throw exception because there are not enough open piles
  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultipleInvalid5() throws Exception {
    this.fcm1.startGame(this.fcm1.getDeck(), 28, 4, false);
    this.fcm1.move(PileType.CASCADE, 22, 1, PileType.CASCADE, 25);
    this.fcm1.move(PileType.CASCADE, 19, 1, PileType.CASCADE, 22);
    this.fcm1.move(PileType.CASCADE, 23, 1, PileType.OPEN, 0);
    this.fcm1.move(PileType.CASCADE, 4, 1, PileType.OPEN, 1);
    this.fcm1.move(PileType.CASCADE, 5, 1, PileType.OPEN, 2);
    this.fcm1.move(PileType.CASCADE, 6, 1, PileType.OPEN, 3);
    this.fcm1.move(PileType.CASCADE, 23, 0, PileType.CASCADE, 25);
    this.fcm1.move(PileType.CASCADE, 27, 0, PileType.CASCADE, 25);
  }

  // should throw exception because the the card index is not the last index
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput1() {
    this.fcm1.move(PileType.OPEN, 2, 1, PileType.OPEN, 1);
  }

  // should throw exception because the pile number is higher than the number of open piles
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput2() {
    this.fcm1.move(PileType.OPEN, 7, 0, PileType.OPEN, 1);
  }

  // should throw exception because the card index is negative
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput3() {
    this.fcm1.move(PileType.OPEN, 2, -3, PileType.OPEN, 1);
  }

  // should throw exception because the pile number is negative
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput4() {
    this.fcm1.move(PileType.OPEN, -2, 0, PileType.OPEN, 1);
  }

  // should throw exception because you cannot move cards in the foundation pile
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput5() {
    this.fcm1.move(PileType.FOUNDATION, 0, 0, PileType.OPEN, 1);
  }

  // should throw exception because the card index is not the lowest in the cascade pile
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput6() {
    this.fcm1.move(PileType.CASCADE, 7, 0, PileType.OPEN, 1);
  }

  // should throw exception because the pile number is higher than the number of cascade piles
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput7() {
    this.fcm1.move(PileType.CASCADE, 15, 2, PileType.OPEN, 1);
  }

  // should throw exception because the card index is higher than the number of cascade piles
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput8() {
    this.fcm1.move(PileType.CASCADE, 1, 22, PileType.OPEN, 1);
  }

  // should throw exception because the destination pile is greater than the number of open piles
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput9() {
    this.fcm1.move(PileType.CASCADE, 2, 2, PileType.OPEN, 7);
  }

  // should throw exception because the foundation pile 4 does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput10() {
    this.fcm1.move(PileType.CASCADE, 2, 2, PileType.FOUNDATION, 4);
  }

  // should throw exception because the cascade pile 23 does not exist
  @Test(expected = IllegalArgumentException.class)
  public void testCheckValidInput11() {
    this.fcm1.move(PileType.CASCADE, 2, 2, PileType.CASCADE, 23);
  }

  // tests moving a list of three cards from one cascade pile to another cascade pile
  @Test
  public void testCascadeMove1() {
    this.fcm1.startGame(this.fcm1.getDeck(), 28, 4, false);
    this.fcm1.move(PileType.CASCADE, 22, 1, PileType.CASCADE, 25);
    this.fcm1.move(PileType.CASCADE, 19, 1, PileType.CASCADE, 22);
    this.fcm1.move(PileType.CASCADE, 23, 1, PileType.OPEN, 0);
    this.fcm1.move(PileType.CASCADE, 4, 1, PileType.OPEN, 1);
    this.fcm1.move(PileType.CASCADE, 23, 0, PileType.CASCADE, 25);
    this.fcm1.move(PileType.CASCADE, 25, 0, PileType.CASCADE, 23);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: K♣\n" +
            "O2: 7♠\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 3♠\n" +
            "C2: 2♥, 4♠\n" +
            "C3: 3♥, 5♠\n" +
            "C4: 4♥, 6♠\n" +
            "C5: 5♥\n" +
            "C6: 6♥, 8♠\n" +
            "C7: 7♥, 9♠\n" +
            "C8: 8♥, 10♠\n" +
            "C9: 9♥, J♠\n" +
            "C10: 10♥, Q♠\n" +
            "C11: J♥, K♠\n" +
            "C12: Q♥, A♣\n" +
            "C13: K♥, 2♣\n" +
            "C14: A♦, 3♣\n" +
            "C15: 2♦, 4♣\n" +
            "C16: 3♦, 5♣\n" +
            "C17: 4♦, 6♣\n" +
            "C18: 5♦, 7♣\n" +
            "C19: 6♦, 8♣\n" +
            "C20: 7♦\n" +
            "C21: 8♦, 10♣\n" +
            "C22: 9♦, J♣\n" +
            "C23: 10♦, 9♣\n" +
            "C24: K♦, Q♣, J♦\n" +
            "C25: Q♦\n" +
            "C26:\n" +
            "C27: A♠\n" +
            "C28: 2♠", this.fcm1.getGameState());
  }
}
