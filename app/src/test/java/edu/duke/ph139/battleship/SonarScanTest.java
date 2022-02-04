package edu.duke.ph139.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class SonarScanTest {

  private String createDiamondCoord(Coordinate c) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= 3; i++) {
      int scanR = c.getRow() - (3 - i);
      int scanC = c.getColumn() - i;
      for (int j = 0; j < i * 2 + 1; j++) {
        Coordinate scanCoord = new Coordinate(scanR, scanC + j);
        sb.append(scanCoord);
      }
      sb.append("\n");
    }
    for (int i = 0; i < 3; i++) {
      int scanR = c.getRow() + 1 + i;
      int scanC = c.getColumn() - (2 - i);
      for (int j = 0; j < 2 * (2 - i) + 1; j++) {
        Coordinate scanCoord = new Coordinate(scanR, scanC + j);
        sb.append(scanCoord);
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  private HashMap<Character, String> setupSonarScanMapForV2() {
    HashMap<Character, String> shipDispMap = new HashMap<>();
    shipDispMap.put('s', "Submarine");
    shipDispMap.put('d', "Destroyer");
    shipDispMap.put('b', "Battleship");
    shipDispMap.put('c', "Carrier");
    return shipDispMap;
  }

  private String createScanResult(int subCount, int desCount, int batCount, int carCount) {
    String result = "Submarines occupy " + subCount + " squares\nDestroyers occupy " + desCount
      + " squares\nBattleships occupy " + batCount + " squares\nCarriers occupy " + carCount + " square\n";
    return result;
  }


  @Test
  public void test_useAction() {
    SonarScan<Character> ss = new SonarScan<>(3, setupSonarScanMapForV2(), '*');
    AbstractShipFactory<Character> f = new V2ShipFactory();
    Board<Character> b = new BattleShipBoard<Character>(10, 20, '*');
    BoardTextView view = new BoardTextView(b);
    b.tryAddShip(f.makeBattleship(new Placement("A0u")));
    Ship<Character> car = f.makeCarrier(new Placement("c0u"));
    b.tryAddShip(car);
    b.tryAddShip(f.makeDestroyer(new Placement("a3h")));
    b.tryAddShip(f.makeSubmarine(new Placement("b3h")));
    System.out.println(view.displayMyOwnBoard());
    
    Coordinate c = new Coordinate(0, 7);
    String actual = ss.useAction(b, c);
    String expected = createScanResult(0, 2, 0, 0);
    /*
    System.out.println();
    System.out.println(createDiamondCoord(c));
    assertEquals(expected, actual);
    */
    c = new Coordinate(2, 2);
    System.out.println();
    System.out.println(createDiamondCoord(c));
    System.out.println();
    car.recordHitAt(new Coordinate(2, 0));
    actual = ss.useAction(b, c);
    expected = createScanResult(2, 1, 4, 3);
    assertEquals(expected, actual);    
  }

}
