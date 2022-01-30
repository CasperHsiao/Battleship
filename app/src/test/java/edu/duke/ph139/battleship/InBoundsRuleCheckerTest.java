package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
    Board<Character> b = new BattleShipBoard<>(10, 20, checker, 'X');
    Ship<Character> s1 = f.makeCarrier(new Placement("A0V"));
    Ship<Character> s2 = f.makeCarrier(new Placement("z0V"));
    Ship<Character> s3 = f.makeCarrier(new Placement("A9h"));
    assertEquals(null, checker.checkPlacement(s1, b));
    Ship<Character> s4 = f.makeCarrier(new Placement("z0V")); //Out bounds
    Ship<Character> s5 = f.makeCarrier(new Placement("A9h")); //Out bounds
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", checker.checkPlacement(s4, b));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", checker.checkPlacement(s5, b));
    Ship<Character> s6 = f.makeCarrier(new Placement(new Coordinate(0, -1), 'H')); //Out bounds
    assertEquals("That placement is invalid: the ship goes off the left of the board.", checker.checkPlacement(s6, b));
    Ship<Character> s7 = f.makeCarrier(new Placement(new Coordinate(-1, 0), 'V')); //Out bounds
    assertEquals("That placement is invalid: the ship goes off the top of the board.", checker.checkPlacement(s7, b));

  }



}
