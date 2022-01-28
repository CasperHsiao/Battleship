package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    PlacementRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
    Board<Character> b = new BattleShipBoard<>(10, 20, checker);
    Ship<Character> s1 = f.makeCarrier(new Placement("A0V"));
    Ship<Character> s2 = f.makeCarrier(new Placement("C1H"));
    Ship<Character> s3 = f.makeCarrier(new Placement("E0V"));
    assertEquals(true, checker.checkPlacement(s1, b));
    b.tryAddShip(s1);
    assertEquals(true, checker.checkPlacement(s2, b));
    b.tryAddShip(s2);
    assertEquals(false, checker.checkPlacement(s3, b));
  }

  @Test
  public void test_checkMyRule_chain_InBoundsRC() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    PlacementRuleChecker<Character> IBChecker = new InBoundsRuleChecker<>(null);
    PlacementRuleChecker<Character> NCChecker = new NoCollisionRuleChecker<>(IBChecker);
    Board<Character> b = new BattleShipBoard<>(10, 20, NCChecker);
    Ship<Character> s1 = f.makeCarrier(new Placement("A0V"));
    Ship<Character> s2 = f.makeCarrier(new Placement("C1H"));
    Ship<Character> s3 = f.makeCarrier(new Placement("E0V"));
    assertEquals(true, NCChecker.checkPlacement(s1, b)); //NC
    b.tryAddShip(s1);
    assertEquals(true, NCChecker.checkPlacement(s2, b)); //NC
    b.tryAddShip(s2);
    assertEquals(false, NCChecker.checkPlacement(s3, b)); //Collison
    Ship<Character> s4 = f.makeCarrier(new Placement("z0V")); //Out bounds
    Ship<Character> s5 = f.makeCarrier(new Placement("A9h")); //Out bounds
    assertEquals(false, NCChecker.checkPlacement(s4, b));
    assertEquals(false, NCChecker.checkPlacement(s5, b));
  }

}
