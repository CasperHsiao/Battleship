
package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));

  }

  @Test
  public void test_hasLost() {
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(true, b.allSunk());
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Ship<Character> s = f.makeSubmarine(new Placement("A0h"));
    b.tryAddShip(s);
    assertEquals(false, b.allSunk());
    b.fireAt(new Coordinate("A0"));
    b.fireAt(new Coordinate("A1"));
    assertEquals(true, b.allSunk());
  }
  
  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < b.getWidth(); i++) {
      for (int j = 0; j < b.getHeight(); j++) {
        assertEquals(b.whatIsAtForSelf(new Coordinate(j, i)), expected[j][i]);
      }
    }
  }

  @Test
  public void test_fireAt_and_whatIsAtForEnemy() {
    Board<Character> b = new BattleShipBoard<>(3, 5, 'X');
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Ship<Character> s1 = f.makeSubmarine(new Placement("a0h"));
    Ship<Character> s2 = f.makeDestroyer(new Placement("a2v"));
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    // attack s1
    assertSame(s1, b.fireAt(new Coordinate(0, 0)));
    assertEquals(true, s1.wasHitAt(new Coordinate(0, 0)));
    assertSame(s1, b.fireAt(new Coordinate(0, 1)));
    assertEquals(true, s1.wasHitAt(new Coordinate(0, 1)));
    assertEquals(true, s1.isSunk());
    // miss s2
    assertEquals(null, b.fireAt(new Coordinate(1, 0)));
    // check what is at for enemy
    assertEquals('X', b.whatIsAtForEnemy(new Coordinate(1, 0)));
    assertEquals('s', b.whatIsAtForEnemy(new Coordinate(0, 0)));
    assertEquals('s', b.whatIsAtForEnemy(new Coordinate(0, 1)));

    // remove the ships
    assertEquals(s1, b.removeShip(new Coordinate(0, 0)));
    assertEquals(s2, b.removeShip(new Coordinate(0, 2)));
    // check what is at for enemy
    assertEquals('X', b.whatIsAtForEnemy(new Coordinate(1, 0)));
    assertEquals('s', b.whatIsAtForEnemy(new Coordinate(0, 0)));
    assertEquals('s', b.whatIsAtForEnemy(new Coordinate(0, 1)));
    // fire at coordinate that was previously hit
    assertEquals(null, b.fireAt(new Coordinate(0, 0)));
    assertEquals('X', b.whatIsAtForEnemy(new Coordinate(0, 0)));
    // fire at coordinate that was previously missed
    Ship<Character> s3 = f.makeDestroyer(new Placement("B0H"));
    b.tryAddShip(s3);
    assertSame(s3, b.fireAt(new Coordinate(1, 0)));
    assertEquals('d', b.whatIsAtForEnemy(new Coordinate(1, 0)));
  }
  
  @Test
  public void test_empty_3by5_board() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');
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
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 5, 'X');
    assertEquals(b1.tryAddShip(s1), null);
    assertEquals(b1.tryAddShip(s2), null);
    assertEquals(b1.tryAddShip(s3), null);
    assertEquals(b1.tryAddShip(s4), null);
    Character[][] expected = { { null, null, null }, { null, null, 's' }, { null, 's', null }, { 's', null, null },
        { null, null, 's' } };
    checkWhatIsAtBoard(b1, expected);

  }

  @Test
  public void test_removeShip() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    Ship<Character> s = f.makeSubmarine(new Placement("A0h"));
    b.tryAddShip(s);
    assertEquals(null, b.removeShip(new Coordinate(1, 0)));
    assertEquals(s, b.removeShip(new Coordinate(0, 0)));
    assertEquals(null, b.removeShip(new Coordinate (0, 0)));
  }

  @Test
  public void test_checkCoordinateInBounds() {
    Board<Character> b = new BattleShipBoard<Character>(3, 3, 'X');
    assertNull(b.checkCoordinateInBounds(new Coordinate(0, 0)));
    assertEquals("The coordinate entered is out of bounds.", b.checkCoordinateInBounds(new Coordinate(-1, 0)));
    assertEquals("The coordinate entered is out of bounds.", b.checkCoordinateInBounds(new Coordinate(0, -1)));
  }

}
