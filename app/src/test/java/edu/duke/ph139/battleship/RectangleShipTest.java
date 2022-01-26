package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    Ship<Character> s1 = new RectangleShip<>(new Coordinate(1, 2), 1, 3, 's', '*');
    HashSet<Coordinate> expected = new HashSet<>();
    expected.add(new Coordinate(1, 2));
    expected.add(new Coordinate(2, 2));
    expected.add(new Coordinate(3, 2));
    for (Coordinate c: expected) {
      assertEquals(s1.occupiesCoordinates(c), true);
    }
  }

}
