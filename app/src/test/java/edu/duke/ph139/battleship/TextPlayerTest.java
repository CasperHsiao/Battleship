package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {

  private TextPlayer generate_basic_player_for_stringReader(String name, OutputStream bytes, int w, int h,
      String placements) {
    BufferedReader input = new BufferedReader(new StringReader(placements));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<>(w, h, 'X');
    AbstractShipFactory<Character> f = new V1ShipFactory();
    return new TextPlayer(b, input, output, f, name);
  }

  @Test
  public void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 10, 20, "B2V\nC8H\na4v\n");
    String prompt = "Please enter a location for a ship: ";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');
    for (int i = 0; i < expected.length; i++) {
      Placement p = p1.readPlacement(prompt);
      assertEquals(p, expected[i]);
      assertEquals(prompt, bytes.toString());
      bytes.reset();
    }
  }

  @Test
  public void test_read_coordinate() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 10, 20, "B2\nC8\na4\n");
    String prompt = "Please enter a coordinate to fire at!\n";
    Coordinate c1 = p1.readCoordinate(prompt);
    assertEquals(c1, new Coordinate(1, 2));
    assertEquals(prompt, bytes.toString());
    bytes.reset();
    Coordinate c2 = p1.readCoordinate(prompt);
    assertEquals(c2, new Coordinate(2, 8));
    assertEquals(prompt, bytes.toString());
    bytes.reset();
    Coordinate c3 = p1.readCoordinate(prompt);
    assertEquals(c3, new Coordinate(0, 4));
    assertEquals(prompt, bytes.toString());
    assertThrows(EOFException.class, () -> p1.readCoordinate(prompt));
    bytes.reset();
  }

  @Test
  public void test_doAttackingPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 10, 6, "Bl\ng2\nb0\na0\n");
    TextPlayer enemy = generate_basic_player_for_stringReader("B", bytes, 10, 6, "");
    Ship<Character> s = enemy.shipFactory.makeSubmarine(new Placement("a0h"));
    enemy.theBoard.tryAddShip(s);
    String dummyHeader = "Player B's ocean";
    // assertThrows(EOFException.class, () -> p1.doAttackingPhase(enemy.theBoard,
    // enemy.view, dummyHeader));
    p1.doAttackingPhase(enemy.theBoard, enemy.view, dummyHeader);
    String turnPrompt = "Player A's turn:\n";
    String boardView1 = "     Your ocean                           Player B's ocean\n  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\nA  | | | | | | | | |  A                A  | | | | | | | | |  A\nB  | | | | | | | | |  B                B  | | | | | | | | |  B\nC  | | | | | | | | |  C                C  | | | | | | | | |  C\nD  | | | | | | | | |  D                D  | | | | | | | | |  D\nE  | | | | | | | | |  E                E  | | | | | | | | |  E\nF  | | | | | | | | |  F                F  | | | | | | | | |  F\n  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n";
    String coordPrompt = "Please enter a coordinate to fire at!\n";
    String invalidCoord = "The coordinate entered is out of bounds.\n\n";
    String hitPrompt = "You hit a submarine!\n\n";
    String missedPrompt = "You missed!\n\n";
    assertEquals(
        turnPrompt + boardView1 + coordPrompt + invalidCoord + coordPrompt + invalidCoord + coordPrompt + missedPrompt,
        bytes.toString());
    bytes.reset();

    p1.doAttackingPhase(enemy.theBoard, enemy.view, dummyHeader);
    String boardView2 = "     Your ocean                           Player B's ocean\n  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\nA  | | | | | | | | |  A                A  | | | | | | | | |  A\nB  | | | | | | | | |  B                B X| | | | | | | | |  B\nC  | | | | | | | | |  C                C  | | | | | | | | |  C\nD  | | | | | | | | |  D                D  | | | | | | | | |  D\nE  | | | | | | | | |  E                E  | | | | | | | | |  E\nF  | | | | | | | | |  F                F  | | | | | | | | |  F\n  0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9\n\n";
    assertEquals(turnPrompt + boardView2 + coordPrompt + hitPrompt, bytes.toString());
    bytes.reset();

  }

  @Test
  public void test_read_placement_EOF() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 10, 20, "");
    String prompt = "Please enter a location for a ship: ";
    assertThrows(EOFException.class, () -> p1.readPlacement(prompt));
    assertEquals(prompt, bytes.toString());
    bytes.reset();
  }

  @Test
  public void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 4, 3, "A0V\nc1h\na3v\n");
    String prompt = "Player A where do you want to place a Destroyer?";
    AbstractShipFactory<Character> f = new V1ShipFactory();
    p1.doOnePlacement("Destroyer", (p) -> f.makeDestroyer(p));
    String expectedHeader = "  0|1|2|3\n";
    String expectedBody = "A d| | |  A\n" + "B d| | |  B\n" + "C d| | |  C\n";
    String expected = expectedHeader + expectedBody + expectedHeader + "\n";
    assertEquals(prompt + "\n" + expected, bytes.toString());
    bytes.reset();

    p1.doOnePlacement("Destroyer", (p) -> f.makeDestroyer(p));
    expectedBody = "A d| | |  A\n" + "B d| | |  B\n" + "C d|d|d|d C\n";
    expected = expectedHeader + expectedBody + expectedHeader + "\n";
    assertEquals(prompt + "\n" + expected, bytes.toString());
    bytes.reset();

    assertThrows(IllegalArgumentException.class, () -> p1.doOnePlacement("Destroyer", (p) -> f.makeDestroyer(p)));

  }

  @Test
  /*
   * Test is tied to 1 submarine, 1 destroyer, 1 battleship placement. Battleship
   * is placed out of bounds.
   */
  public void test_doPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 4, 3, "A0V\na1h\nc0v\n");
    assertThrows(EOFException.class, () -> p1.doPlacementPhase());
    String startPrompt = "Player A: you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n\n";
    String expectedHeader = "  0|1|2|3\n";
    String expectedBodyBefore = "A  | | |  A\n" + "B  | | |  B\n" + "C  | | |  C\n";
    String prompt1 = "Player A where do you want to place a Submarine?\n";
    String expectedBefore = expectedHeader + expectedBodyBefore + expectedHeader + "\n";
    String expectedBodyAfter1 = "A s| | |  A\n" + "B s| | |  B\n" + "C  | | |  C\n";
    String expectedAfter1 = expectedHeader + expectedBodyAfter1 + expectedHeader + "\n";
    String prompt2 = "Player A where do you want to place a Destroyer?\n";
    String expectedBodyAfter2 = "A s|d|d|d A\n" + "B s| | |  B\n" + "C  | | |  C\n";
    String expectedAfter2 = expectedHeader + expectedBodyAfter2 + expectedHeader + "\n";

    String prompt3 = "Player A where do you want to place a Battleship?\n";

    /*
     * String expectedBodyAfter3 = "A s|d|d|d A\n" + "B s| | |  B\n" +
     * "C b|b|b|b C\n"; String expectedAfter3 = expectedHeader + expectedBodyAfter3
     * + expectedHeader;
     */
    String errMsg = "That placement is invalid: the ship goes off the bottom of the board.\n\n";
    assertEquals(
        expectedBefore + startPrompt + prompt1 + expectedAfter1 + prompt2 + expectedAfter2 + prompt3 + errMsg + prompt3,
        bytes.toString());
    bytes.reset();

  }
}
