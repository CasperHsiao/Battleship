package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
  @Test
  public void test_getCoordinates() {
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Ship<Character> s = f.makeBattleship(new Placement("A0H"));
    HashSet<Coordinate> set = new HashSet<>();
    set.add(new Coordinate("A0"));
    set.add(new Coordinate("A1"));
    set.add(new Coordinate("A2"));
    set.add(new Coordinate("a3"));
    assertEquals(set, s.getCoordinates());
    
  }

}
