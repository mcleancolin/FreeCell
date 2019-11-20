package cs3500.hw03;

import org.junit.Test;

import java.io.StringReader;

import java.io.StringWriter;

import cs3500.hw02.FreecellModel;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the FreecellController class.
 */
public class FreecellControllerTest {
  FreecellController fc1;
  FreecellModel fm1;

  public FreecellControllerTest() {
    this.fm1 = new FreecellModel();
    this.fc1 = new FreecellController(new StringReader(""), new StringWriter());
  }

  @Test
  public void testCurrentGameState() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    this.fc1 = new FreecellController(new StringReader("Q"), new StringWriter());
    this.fc1.playGame(this.fm1.getDeck(), this.fm1, 8, 4, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 9♥, 4♦, Q♦, 7♠, 2♣, 10♣\n" +
            "C2: 2♥, 10♥, 5♦, K♦, 8♠, 3♣, J♣\n" +
            "C3: 3♥, J♥, 6♦, A♠, 9♠, 4♣, Q♣\n" +
            "C4: 4♥, Q♥, 7♦, 2♠, 10♠, 5♣, K♣\n" +
            "C5: 5♥, K♥, 8♦, 3♠, J♠, 6♣\n" +
            "C6: 6♥, A♦, 9♦, 4♠, Q♠, 7♣\n" +
            "C7: 7♥, 2♦, 10♦, 5♠, K♠, 8♣\n" +
            "C8: 8♥, 3♦, J♦, 6♠, A♣, 9♣\n" +
            "Game quit prematurely.", this.fc1.ap.toString());
  }

  // should throw an exception because the readable is null
  @Test(expected = IllegalStateException.class)
  public void testPlayGameException1() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    this.fc1 = new FreecellController(null, new StringWriter());
  }

  // should throw an exception because both the readable and the appendable are null
  @Test(expected = IllegalStateException.class)
  public void testPlayGameException2() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    this.fc1 = new FreecellController(null, null);
  }

  // should throw an exception because the appendable is null
  @Test(expected = IllegalStateException.class)
  public void testPlayGameException3() {
    this.fm1.startGame(fm1.getDeck(), 8, 4, false);
    this.fc1 = new FreecellController(new StringReader("C1 Q"), null);
  }

  // test to catch the exception if the pile parameters are too low
  @Test (expected = IllegalArgumentException.class)
  public void testPlayGameException4() {
    this.fm1.startGame(fm1.getDeck(), 2, 4, false);
  }

  @Test
  public void testGameOver1() {
    this.fm1.startGame(fm1.getDeck(), 4, 4, false);
    this.fc1 = new FreecellController(new StringReader("Q"), new StringWriter());
    int count = 0;
    for (int i = 0; i < this.fm1.getDeck().size(); i++) {
      if (i % 13 == 0) {
        count++;
      }
      this.fm1.setFoundation2(count - 1, this.fm1.getDeck().get(i));
    }
    assertEquals(true, this.fm1.isGameOver());
    this.fc1.playGame(this.fm1.getDeck(), this.fm1, 8, 4, false);
    assertEquals("F1: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
            "F2: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
            "F3: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
            "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n" +
            "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
            "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n" +
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "Game over.", this.fc1.ap.toString());
  }

  @Test
  public void testPlayGame1() {
    this.fm1.startGame(fm1.getDeck(), 4, 4, false);
    this.fc1 = new FreecellController(new StringReader("C1 13 O1 Q"), new StringWriter());
    this.fc1.playGame(this.fm1.getDeck(), this.fm1, 4, 4, false);
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
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 10♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣\n" +
            "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
            "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n" +
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "Game quit prematurely.", this.fc1.ap.toString());
  }

  @Test
  public void testPlayGame2() {
    this.fm1.startGame(fm1.getDeck(), 4, 4, false);
    this.fc1 = new FreecellController(new StringReader("CC1 C1 13 O1  Q"), new StringWriter());
    this.fc1.playGame(this.fm1.getDeck(), this.fm1, 4, 4, false);
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
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "Sorry, the pile or card number must be an integer. Please enter a valid integer.\n" +
            "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 10♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣\n" +
            "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
            "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n" +
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "Game quit prematurely.", this.fc1.ap.toString());
  }

  @Test
  public void testPlayGame3() {
    this.fm1.startGame(fm1.getDeck(), 4, 4, false);
    this.fc1 = new FreecellController(new StringReader("C1 1O3 13 O1 Q"), new StringWriter());
    this.fc1.playGame(this.fm1.getDeck(), this.fm1, 4, 4, false);
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
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "Sorry, the pile or card number must be an integer. Please enter a valid integer.\n" +
            "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 10♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣\n" +
            "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
            "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n" +
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "Game quit prematurely.", this.fc1.ap.toString());
  }

  @Test
  public void testPlayGame4() {
    this.fm1.startGame(fm1.getDeck(), 4, 4, false);
    this.fc1 = new FreecellController(new StringReader("C1 13 OO1 O1  Q"), new StringWriter());
    this.fc1.playGame(this.fm1.getDeck(), this.fm1, 4, 4, false);
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
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "Sorry, the pile or card number must be an integer. Please enter a valid integer.\n" +
            "F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: 10♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣\n" +
            "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
            "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n" +
            "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣\n" +
            "Game quit prematurely.", this.fc1.ap.toString());
  }
}
