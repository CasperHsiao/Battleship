package edu.duke.ph139.battleship;

import java.util.HashMap;

public class SonarScan<T> {
  int movesLeft;
  final static int SCAN_RADIUS = 3;

  public SonarScan(int n) {
    this.movesLeft = n;
  }

  public String useAction(Board<T> enemyBoard, Coordinate c) {
    HashMap<String, Integer> shipCount = new HashMap<>();
    shipCount.put("Submarine", 0);
    shipCount.put("Destroyer", 0);
    shipCount.put("Battleship", 0);
    shipCount.put("Carrier", 0);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= 2 * SCAN_RADIUS; i++) {
      int drow = i - SCAN_RADIUS;
      int dcol = Math.abs(i - SCAN_RADIUS) - SCAN_RADIUS;
      int n = 2 * (SCAN_RADIUS - Math.abs(i - SCAN_RADIUS));
      for (int j = 0; j <= n; j++) {
        Coordinate scanCoord = new Coordinate(c.getRow() + drow, c.getColumn() + dcol + j);
        String shipName = enemyBoard.whatIsAtForSonar(scanCoord);
        if (shipName == null) {
          continue;
        }
        int count = shipCount.get(shipName);
        shipCount.replace(shipName, count + 1);
        
      }
    }
    this.movesLeft--;
    String result = "Submarines occupy " + shipCount.get("Submarine") + " squares\nDestroyers occupy " + shipCount.get("Destroyer")
      + " squares\nBattleships occupy " + shipCount.get("Battleship") + " squares\nCarriers occupy " + shipCount.get("Carrier") + " square\n";
    return result;
  }

  public int getMovesLeft() {
    return movesLeft;
  }

}
