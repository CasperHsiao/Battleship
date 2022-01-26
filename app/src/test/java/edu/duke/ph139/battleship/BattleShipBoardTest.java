package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));

  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < b.getWidth(); i++) {
      for (int j = 0; j < b.getHeight(); j++) {
        assertEquals(b.whatIsAt(new Coordinate(j, i)), expected[j][i]);
      }
    }
  }

  @Test
  public void test_empty_3by5_board() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 5);
    Character[][] expected = new Character[5][3];
    checkWhatIsAtBoard(b1, expected);
  }

  @Test
  public void test_basic_add_ships_on_3by5() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(4, 2);
    Coordinate c3 = new Coordinate(3, 0);
    Coordinate c4 = new Coordinate(2, 1);
    Ship<Character> s1 = new RectangleShip<>(c1, 's', '*');
    Ship<Character> s2 = new RectangleShip<>(c2, 's', '*');
    Ship<Character> s3 = new RectangleShip<>(c3, 's', '*');
    Ship<Character> s4 = new RectangleShip<>(c4, 's', '*');
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 5);
    assertEquals(b1.tryAddShip(s1), true);
    assertEquals(b1.tryAddShip(s2), true);
    assertEquals(b1.tryAddShip(s3), true);
    assertEquals(b1.tryAddShip(s4), true);
    Character[][] expected = { { null, null, null }, { null, null, 's' }, { null, 's', null }, { 's', null, null },
        { null, null, 's' } };
    checkWhatIsAtBoard(b1, expected);

  }

}
