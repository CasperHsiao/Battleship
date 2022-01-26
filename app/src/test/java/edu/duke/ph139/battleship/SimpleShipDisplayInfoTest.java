package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_get_info() {
    ShipDisplayInfo<Character> disp = new SimpleShipDisplayInfo<>('s', '*');
    assertEquals('*', disp.getInfo(new Coordinate(0, 0), true));
  }

}
