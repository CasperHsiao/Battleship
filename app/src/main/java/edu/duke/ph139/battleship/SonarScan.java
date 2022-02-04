package edu.duke.ph139.battleship;

import java.util.HashMap;

public class SonarScan<T> {
  final int movesLeft;
  final HashMap<T, String> shipDispMap;
  final T onHit;
  final static int SCAN_RADIUS = 3;

  public SonarScan(int n, HashMap<T, String> shipDispMap, T onHit) {
    this.movesLeft = n;
    this.shipDispMap = shipDispMap;
    this.onHit = onHit;
  }

  public String useAction(Board<T> enemyBoard, Coordinate c) {
    int subCount = 0;
    int desCount = 0;
    int batCount = 0;
    int carCount = 0;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= 2 * SCAN_RADIUS; i++) {
      int drow = i - SCAN_RADIUS;
      int dcol = Math.abs(i - SCAN_RADIUS) - SCAN_RADIUS;
      int n = 2 * (SCAN_RADIUS - Math.abs(i - SCAN_RADIUS));
      for (int j = 0; j <= n; j++) {
        Coordinate scanCoord = new Coordinate(c.getRow() + drow, c.getColumn() + dcol + j);
        System.out.print(scanCoord);
        T disp = enemyBoard.whatIsAtForSonar(scanCoord, onHit);
        if (disp == null) {
          continue;
        }
        String info = shipDispMap.get(disp);
        if (info.equals("Submarine")) {
          subCount++;
        } else if (info.equals("Destroyer")) {
          desCount++;
        } else if (info.equals("Battleship")) {
          batCount++;
        } else if (info.equals("Carrier")) {
          carCount++;
        }
      }
    }
    
    String result = "Submarines occupy " + subCount + " squares\nDestroyers occupy " + desCount
      + " squares\nBattleships occupy " + batCount + " squares\nCarriers occupy " + carCount + " square\n";

    return result;
  }

  public int getMovesLeft() {
    return movesLeft;
  }

}
