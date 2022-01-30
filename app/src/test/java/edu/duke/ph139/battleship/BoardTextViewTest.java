
package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  @Test
  public void test_display_empty_2by2() {
    String expectedHeader = "  0|1\n";
    String expectedBody = "A  |  A\n" + "B  |  B\n";
    emptyBoardHelper(2, 2, expectedHeader, expectedBody);
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> b1 = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(b1));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(b2));
  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" + "B  | |  B\n";
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }

  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n" + "B  | |  B\n" + "C  | |  C\n" + "D  | |  D\n" + "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  }

  @Test
  public void test_display_non_empty_4by3() {
    Board<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b1);
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(0, 2);
    Coordinate c3 = new Coordinate(2, 0);
    Coordinate c4 = new Coordinate(2, 1);
    Ship<Character> s1 = new RectangleShip<>(c1, 's', '*');
    Ship<Character> s2 = new RectangleShip<>(c2, 's', '*');
    Ship<Character> s3 = new RectangleShip<>(c3, 's', '*');
    Ship<Character> s4 = new RectangleShip<>(c4, 's', '*');
    assertEquals(b1.tryAddShip(s1), null);
    assertEquals(b1.tryAddShip(s2), null);
    String expectedHeader = "  0|1|2|3\n";
    String expectedBody = "A  | |s|  A\n" + "B  | |s|  B\n" + "C  | | |  C\n";
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
    assertEquals(b1.tryAddShip(s3), null);
    assertEquals(b1.tryAddShip(s4), null);
    Character[][] expectedArray = { { null, null, 's', null}, { null, null, 's', null}, { 's', 's', null, null}};
    expectedBody = "A  | |s|  A\n" + "B  | |s|  B\n" + "C s|s| |  C\n";
    expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
    
   }

  @Test
  public void test_displayEnemyBoard() {
    Board<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b1);
    AbstractShipFactory<Character> f = new V1ShipFactory();
    Ship<Character> s1 = f.makeSubmarine(new Placement("A0h"));
    b1.tryAddShip(s1);
    String expectedHeader = "  0|1|2|3\n";
    String myView1 =  "A s|s| |  A\n" + "B  | | |  B\n" + "C  | | |  C\n";
    String enemyView1 =  "A  | | |  A\n" + "B  | | |  B\n" + "C  | | |  C\n";
    assertEquals(expectedHeader + myView1 + expectedHeader, view.displayMyOwnBoard());
    assertEquals(expectedHeader + enemyView1 + expectedHeader, view.displayEnemyBoard());
    b1.fireAt(new Coordinate(0, 0));
    String myView2 =  "A *|s| |  A\n" + "B  | | |  B\n" + "C  | | |  C\n";
    String enemyView2 =  "A s| | |  A\n" + "B  | | |  B\n" + "C  | | |  C\n";
    assertEquals(expectedHeader + myView2 + expectedHeader, view.displayMyOwnBoard());
    assertEquals(expectedHeader + enemyView2 + expectedHeader, view.displayEnemyBoard());
    b1.fireAt(new Coordinate(1, 0));
    String myView3 =  "A *|s| |  A\n" + "B  | | |  B\n" + "C  | | |  C\n";
    String enemyView3 =  "A s| | |  A\n" + "B X| | |  B\n" + "C  | | |  C\n";
    assertEquals(expectedHeader + myView3 + expectedHeader, view.displayMyOwnBoard());
    assertEquals(expectedHeader + enemyView3 + expectedHeader, view.displayEnemyBoard());

  }
}
