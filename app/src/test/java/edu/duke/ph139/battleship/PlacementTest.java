package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("B3V");
    assertEquals(1, p1.getWhere().getRow());
    assertEquals(3, p1.getWhere().getColumn());
    assertEquals('V', p1.getOrientation());
  }

  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12H"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("00x"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A]V"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0"));
  }

  @Test
  public void test_coordinate_constructor_valid_cases() {
    Coordinate c1 = new Coordinate(3, 6);
    char orien = 'H';
    Placement p1 = new Placement(c1, orien);
    assertEquals(3, p1.getWhere().getRow());
    assertEquals(6, p1.getWhere().getColumn());
    assertEquals('H', p1.getOrientation());
  }

  @Test
  public void test_coordinate_constructor_error_cases() {
    Coordinate c1 = new Coordinate(3, 6);
    char orien = 'Z';
    assertThrows(IllegalArgumentException.class, () -> new Placement(c1, orien));
  }

  @Test
  public void test_equals() {
    Placement p1 = new Placement("B3H");
    Placement p2 = new Placement("B3H");
    Placement p3 = new Placement("B3V");
    Placement p4 = new Placement("A3H");
    assertEquals(p1, p2);
    assertNotEquals(p1, p3);
    assertNotEquals(p1, p4);
    assertNotEquals(p1, "B3H");

  }

  @Test
  public void test_hashCode() {
    Placement p1 = new Placement("B3H");
    Placement p2 = new Placement("B3H");
    Placement p3 = new Placement("B3V");
    Placement p4 = new Placement("A3H");
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());



  }
}
