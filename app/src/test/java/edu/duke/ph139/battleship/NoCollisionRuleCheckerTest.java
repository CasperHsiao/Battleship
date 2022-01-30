package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
    Board<Character> b = new BattleShipBoard<>(10, 20, checker, 'X');
    Ship<Character> s1 = f.makeCarrier(new Placement("A0V"));
    Ship<Character> s2 = f.makeCarrier(new Placement("C1H"));
    Ship<Character> s3 = f.makeCarrier(new Placement("E0V"));
    assertEquals(null, checker.checkPlacement(s1, b));
    b.tryAddShip(s1);
    assertEquals(null, checker.checkPlacement(s2, b));
    b.tryAddShip(s2);
    assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkPlacement(s3, b));
  }

  @Test
  public void test_checkMyRule_chain_InBoundsRC() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    PlacementRuleChecker<Character> IBChecker = new InBoundsRuleChecker<>(null);
    PlacementRuleChecker<Character> NCChecker = new NoCollisionRuleChecker<>(IBChecker);
    Board<Character> b = new BattleShipBoard<>(10, 20, NCChecker, 'X');
    Ship<Character> s1 = f.makeCarrier(new Placement("A0V"));
    Ship<Character> s2 = f.makeCarrier(new Placement("C1H"));
    Ship<Character> s3 = f.makeCarrier(new Placement("E0V"));
    assertEquals(null, NCChecker.checkPlacement(s1, b)); //NC
    b.tryAddShip(s1);
    assertEquals(null, NCChecker.checkPlacement(s2, b)); //NC
    b.tryAddShip(s2);
    assertEquals("That placement is invalid: the ship overlaps another ship.", NCChecker.checkPlacement(s3, b)); //Collison
    Ship<Character> s4 = f.makeCarrier(new Placement("z0V")); //Out bounds
    Ship<Character> s5 = f.makeCarrier(new Placement("A9h")); //Out bounds
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", NCChecker.checkPlacement(s4, b));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", NCChecker.checkPlacement(s5, b));
    Ship<Character> s6 = f.makeCarrier(new Placement(new Coordinate(17, -1), 'H')); //Out bounds
    assertEquals("That placement is invalid: the ship goes off the left of the board.", NCChecker.checkPlacement(s6, b));
    Ship<Character> s7 = f.makeCarrier(new Placement(new Coordinate(-1, 9), 'V')); //Out bounds
    assertEquals("That placement is invalid: the ship goes off the top of the board.", NCChecker.checkPlacement(s7, b));

  }

}
