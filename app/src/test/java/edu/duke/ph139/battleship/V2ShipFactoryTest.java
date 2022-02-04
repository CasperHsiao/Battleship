package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter,
      Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);

    for (int i = 0; i < expectedLocs.length; i++) {
      Coordinate c = expectedLocs[i];
      assertEquals(true, testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
      assertEquals(i, testShip.getIndexOfShipCoordinate(c));
      assertEquals(c, testShip.getShipCoordinateByIndex(i));
    }
  }

  private void checkBattleship(Ship<Character> testShip, Coordinate... expectedLocs) {
    checkShip(testShip, "Battleship", 'b', expectedLocs);
  }

  private void checkCarrier(Ship<Character> testShip, Coordinate... expectedLocs) {
    checkShip(testShip, "Carrier", 'c', expectedLocs);
  }

  @Test
  public void test_inherited_ship_make() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Ship<Character> sub = f.makeSubmarine(new Placement("A0H"));
    checkShip(sub, "Submarine", 's', new Coordinate(0, 0), new Coordinate(0, 1));
    assertThrows(IllegalArgumentException.class, () -> sub.getShipCoordinateByIndex(2));
    Ship<Character> des = f.makeDestroyer(new Placement("X9V"));
    checkShip(des, "Destroyer", 'd', new Coordinate(23, 9), new Coordinate(24, 9), new Coordinate(25, 9));
    assertThrows(IllegalArgumentException.class, () -> des.getShipCoordinateByIndex(3));
  }

  @Test
  public void test_battleship_make() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Ship<Character> batU = f.makeBattleship(new Placement("A0u"));
    checkBattleship(batU, new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2));
    Ship<Character> batR = f.makeBattleship(new Placement("X0r"));
    checkBattleship(batR, new Coordinate(24, 1), new Coordinate(23, 0), new Coordinate(24, 0), new Coordinate(25, 0));
    Ship<Character> batD = f.makeBattleship(new Placement("b0d"));
    checkBattleship(batD, new Coordinate(2, 1), new Coordinate(1, 2), new Coordinate(1, 1), new Coordinate(1, 0));
    Ship<Character> batL = f.makeBattleship(new Placement("c8l"));
    checkBattleship(batL, new Coordinate(3, 8), new Coordinate(4, 9), new Coordinate(3, 9), new Coordinate(2, 9));
    assertThrows(IllegalArgumentException.class, () -> f.makeBattleship(new Placement("A0v")));
    assertThrows(IllegalArgumentException.class, () -> batL.getShipCoordinateByIndex(4));
  }

  @Test
  public void test_carrier_make() {
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Ship<Character> carU = f.makeCarrier(new Placement("A0u"));
    checkCarrier(carU, new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3, 0),
        new Coordinate(2, 1), new Coordinate(3, 1), new Coordinate(4, 1));
    Ship<Character> carR = f.makeCarrier(new Placement("x0R"));
    checkCarrier(carR, new Coordinate(23, 4), new Coordinate(23, 3), new Coordinate(23, 2), new Coordinate(23, 1),
        new Coordinate(24, 2), new Coordinate(24, 1), new Coordinate(24, 0));
    Ship<Character> carD = f.makeCarrier(new Placement("c5d"));
    checkCarrier(carD, new Coordinate(6, 6), new Coordinate(5, 6), new Coordinate(4, 6), new Coordinate(3, 6),
        new Coordinate(4, 5), new Coordinate(3, 5), new Coordinate(2, 5));
    Ship<Character> carL = f.makeCarrier(new Placement("y0l"));
    checkCarrier(carL, new Coordinate(25, 0), new Coordinate(25, 1), new Coordinate(25, 2), new Coordinate(25, 3),
        new Coordinate(24, 2), new Coordinate(24, 3), new Coordinate(24, 4));
    assertThrows(IllegalArgumentException.class, () -> f.makeCarrier(new Placement("p2h")));
    assertThrows(IllegalArgumentException.class, () -> carL.getShipCoordinateByIndex(7));
  }
}
