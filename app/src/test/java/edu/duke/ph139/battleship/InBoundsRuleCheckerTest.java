package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
    Board<Character> b = new BattleShipBoard<>(10, 20, checker);
    Ship<Character> s1 = f.makeCarrier(new Placement("A0V"));
    Ship<Character> s2 = f.makeCarrier(new Placement("z0V"));
    Ship<Character> s3 = f.makeCarrier(new Placement("A9h"));
    assertEquals(true, checker.checkPlacement(s1, b));
    assertEquals(false, checker.checkPlacement(s2, b));
    assertEquals(false, checker.checkPlacement(s3, b));
  }



}
