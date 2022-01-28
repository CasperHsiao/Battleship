package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    HashSet<Coordinate> shipCoordinates = RectangleShip.makeCoords(new Coordinate(1, 2), 1, 3);
    HashSet<Coordinate> expected = new HashSet<>();
    expected.add(new Coordinate(1, 2));
    expected.add(new Coordinate(2, 2));
    expected.add(new Coordinate(3, 2));
    assertEquals(shipCoordinates, expected);

    shipCoordinates = RectangleShip.makeCoords(new Coordinate(21, 5), 3, 2);
    expected = new HashSet<>();
    expected.add(new Coordinate(21, 5));
    expected.add(new Coordinate(21, 6));
    expected.add(new Coordinate(21, 7));
    expected.add(new Coordinate(22, 5));
    expected.add(new Coordinate(22, 6));
    expected.add(new Coordinate(22, 7));
    assertEquals(shipCoordinates, expected);
  }

  @Test
  public void test_constructor() {
    Coordinate cTest = new Coordinate(1, 2);
    Ship<Character> s1 = new RectangleShip<>("submarine", cTest, 1, 3, 's', '*');
    ShipDisplayInfo<Character> subDisp = new SimpleShipDisplayInfo<Character>('s', '*');
    Ship<Character> s2 = new RectangleShip<>("submarine", cTest, 1, 3, subDisp);
    HashSet<Coordinate> expected = new HashSet<>();
    expected.add(new Coordinate(1, 2));
    expected.add(new Coordinate(2, 2));
    expected.add(new Coordinate(3, 2));
    for (Coordinate c : expected) {
      assertEquals(s1.occupiesCoordinates(c), true);
      assertEquals(s2.occupiesCoordinates(c), true);
    }
  }

  @Test
  public void test_hit_tracking() {
    Ship<Character> s1 = new RectangleShip<>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
    assertThrows(IllegalArgumentException.class, () -> s1.recordHitAt(new Coordinate(0, 0)));
    Coordinate hitCoord = new Coordinate(1, 2);
    s1.recordHitAt(hitCoord);
    assertEquals(true, s1.wasHitAt(hitCoord));
    assertEquals(false, s1.isSunk());
    assertEquals('*', s1.getDisplayInfoAt(hitCoord));
    assertEquals('s', s1.getDisplayInfoAt(new Coordinate(2, 2)));
    
    s1.recordHitAt(new Coordinate(2, 2));
    s1.recordHitAt(new Coordinate(3, 2));
    assertEquals(true, s1.isSunk());
  }

  @Test
  public void test_getName() {
    Ship<Character> s1 = new RectangleShip<>(new Coordinate(1, 2), 's', '*');
    assertEquals("testship", s1.getName());
  }


}
