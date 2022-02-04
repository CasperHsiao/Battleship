package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SonarScanTest {
  @Test
  public void test_useAction() {
    SonarScan<Character> ss = new SonarScan<>(3);
    ss.useAction(new BattleShipBoard<>(10, 20, 'X'),new Coordinate(0, 0));
  }

}
