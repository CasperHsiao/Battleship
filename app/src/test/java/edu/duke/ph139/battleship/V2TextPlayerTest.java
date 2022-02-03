package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class V2TextPlayerTest {

  private V2TextPlayer generate_basic_player_for_stringReader(String name, OutputStream bytes, int w, int h,
      String placements) {
    BufferedReader input = new BufferedReader(new StringReader(placements));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<>(w, h, 'X');
    AbstractShipFactory<Character> f = new V1ShipFactory();
    return new V2TextPlayer(b, input, output, f, name);
  }

  @Test
  public void test_readAction() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V2TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 10, 20, "F\nm\ns\nl\n");
    assertEquals('F', p1.readAction("test"));
    assertEquals("test", bytes.toString());
    assertEquals('M', p1.readAction(""));
    assertEquals('S', p1.readAction(""));
    assertThrows(IllegalArgumentException.class, () -> p1.readAction(""));
    assertThrows(IOException.class, () -> p1.readAction(""));
  }

  @Test
  public void test_playOneTurn() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    V2TextPlayer p1 = generate_basic_player_for_stringReader("A", bytes, 10, 20, "F\nA0\ns\nl\nm\n");
    Board<Character> b = new BattleShipBoard<Character>(3, 3, 'X');
    AbstractShipFactory<Character> f = new V2ShipFactory();
    b.tryAddShip(f.makeSubmarine(new Placement("A0h")));
    p1.playOneTurn(b);
    String actionPrompt = "Possible actions for Player " + "A"
        + ":\n\n F Fire at a square\n M Move a ship to another square (" + "0X0X" + " remaining)\n S Sonar scan ("
        + "0X0X" + " remaining)\n\nPlayer " + "A" + ", what would you like to do?\n";
    String firePrompt = "Please enter a coordinate to fire at!\n";
    String hitPrompt = "You hit a submarine!\n\n";
    assertEquals(actionPrompt + firePrompt + hitPrompt, bytes.toString());
    assertThrows(IllegalArgumentException.class, () -> p1.playOneTurn(b));
    assertThrows(IllegalArgumentException.class, () -> p1.playOneTurn(b));
    assertThrows(IllegalArgumentException.class, () -> p1.playOneTurn(b));
  }
}
