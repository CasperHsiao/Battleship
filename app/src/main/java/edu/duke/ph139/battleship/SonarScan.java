package edu.duke.ph139.battleship;

public class SonarScan<T> {
  final int movesLeft;

  public SonarScan(int n) {
    this.movesLeft = n;
  }

  public String useAction(Board<T> enemyBoard, Coordinate c) {
    int subCount = 0;
    int desCount = 0;
    int batCount = 0;
    int carCount = 0;
    int row = c.getRow();
    int col = c.getColumn();
    for (int i = 0; i <= 3; i++) {
      int scanR = row - (3 - i);
      int scanC = col - i;
      for (int j = 0; j < i * 2 + 1; j++) {
        Coordinate scanCoord = new Coordinate(scanR, scanC + j);
        System.out.print(scanCoord);
      }
      System.out.println("");
    }
    for (int i = 0; i < 3; i++) {
      int scanR = row + 1 + i;
      int scanC = col - (2 - i);
      for (int j = 0; j < 2 * (2 - i) + 1; j++) {
        Coordinate scanCoord = new Coordinate(scanR, scanC + j);
        System.out.print(scanCoord);
      }
      System.out.println("");
    }
    

    String result = "Submarines occupy " + subCount + " squares\nDestroyers occupy " + desCount + " squares\nBattleships occupy " + batCount + " squares\nCarriers occupy " + carCount + " square\n";
    return null;
  }

  public int getMovesLeft() {
    return movesLeft;
  }
}
