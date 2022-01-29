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
    Board<Character> b = new BattleShipBoard<>(w, h);
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
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(prompt + "\n" + expected, bytes.toString());
    bytes.reset();

    p1.doOnePlacement("Destroyer", (p) -> f.makeDestroyer(p));
    expectedBody = "A d| | |  A\n" + "B d| | |  B\n" + "C d|d|d|d C\n";
    expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(prompt + "\n" + expected, bytes.toString());
    bytes.reset();

    assertThrows(IllegalArgumentException.class, () -> p1.doOnePlacement("Destroyer", (p) -> f.makeDestroyer(p)));

  }


  @Test
  /*
   * Test is tied to 1 submarine, 1 destroyer, 1 battleship placement. Battleship is placed out of bounds.
   */
  public void test_doPlacementPhase() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 4, 3, "A0V\na1h\nc0v\n");
    assertThrows(EOFException.class, () -> p1.doPlacementPhase());
    String startPrompt = "Player A: you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n";
    String expectedHeader = "  0|1|2|3\n";
    String expectedBodyBefore = "A  | | |  A\n" + "B  | | |  B\n" + "C  | | |  C\n";
    String expectedBefore = expectedHeader + expectedBodyBefore + expectedHeader;
    String prompt1 = "Player A where do you want to place a Submarine?\n";
    String expectedBodyAfter1 = "A s| | |  A\n" + "B s| | |  B\n" + "C  | | |  C\n";
    String expectedAfter1 = expectedHeader + expectedBodyAfter1 + expectedHeader;
    String prompt2 = "Player A where do you want to place a Destroyer?\n";
    String expectedBodyAfter2 = "A s|d|d|d A\n" + "B s| | |  B\n" + "C  | | |  C\n";
    String expectedAfter2 = expectedHeader + expectedBodyAfter2 + expectedHeader;
    
    String prompt3 = "Player A where do you want to place a Battleship?\n";

    /*
    String expectedBodyAfter3 = "A s|d|d|d A\n" + "B s| | |  B\n" + "C b|b|b|b C\n";
    String expectedAfter3 = expectedHeader + expectedBodyAfter3 + expectedHeader;
    */
    String errMsg = "That placement is invalid: the ship goes off the bottom of the board.\n";
    assertEquals(expectedBefore + startPrompt + prompt1 + expectedAfter1 + prompt2 + expectedAfter2 + prompt3 + errMsg + prompt3, bytes.toString());
    bytes.reset();
    
  }
}
