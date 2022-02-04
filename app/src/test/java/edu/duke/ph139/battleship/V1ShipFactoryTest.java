package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);
    for (Coordinate c : expectedLocs) {
      assertEquals(true, testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
    }
  }

  @Test
  public void test_all_ship_make() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Ship<Character> sub = f.makeSubmarine(new Placement("A1V"));
    checkShip(sub, "Submarine", 's', new Coordinate(0, 1), new Coordinate(1, 1));
    Ship<Character> bat = f.makeBattleship(new Placement("B8H"));
    checkShip(bat, "Battleship", 'b', new Coordinate(1, 8), new Coordinate(1, 9), new Coordinate(1, 10),
        new Coordinate(1, 11));
    Ship<Character> des = f.makeDestroyer(new Placement("z6h"));
    checkShip(des, "Destroyer", 'd', new Coordinate(25, 6), new Coordinate(25, 7), new Coordinate(25, 8));
    Ship<Character> car = f.makeCarrier(new Placement("e3v"));
    checkShip(car, "Carrier", 'c', new Coordinate(4, 3), new Coordinate(5, 3), new Coordinate(6, 3),
        new Coordinate(7, 3), new Coordinate(8, 3), new Coordinate(9, 3));
  }

  @Test
  public void test_all_ship_make_error() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    assertThrows(IllegalArgumentException.class, () -> f.makeCarrier(new Placement("e3l")));
    assertThrows(IllegalArgumentException.class, () -> f.makeBattleship(new Placement("z3Hv")));
    assertThrows(IllegalArgumentException.class, () -> f.makeDestroyer(new Placement("3av")));
    assertThrows(IllegalArgumentException.class, () -> f.makeCarrier(new Placement("a0u")));
  }

}
