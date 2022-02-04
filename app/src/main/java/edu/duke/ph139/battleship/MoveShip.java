package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class MoveShip<T> {
  int movesLeft;

  public MoveShip(int n) {
    this.movesLeft = n;
  }

  
  public void shipTranslation(Ship<T> toMove, Ship<T> newShip) {
    ArrayList<Integer> hits = new ArrayList<>();
    for (Coordinate shipCoord : toMove.getCoordinates()) {
      if (toMove.wasHitAt(shipCoord)) {
        hits.add(toMove.getIndexOfShipCoordinate(shipCoord));
      }
    }
    for (Integer idx : hits) {
      Coordinate oldHit = newShip.getShipCoordinateByIndex(idx);
      newShip.recordHitAt(oldHit);
    } 
  }

  public void useAction(Board<T> theBoard, Ship<T> toMove, Ship<T> newShip) {
    theBoard.removeShip(toMove);
    try {
      String addShipResult = theBoard.tryAddShip(newShip);
      if (addShipResult != null) {
        throw new IllegalArgumentException(addShipResult);
      }
      shipTranslation(toMove, newShip);
      this.movesLeft--;
    } catch (IllegalArgumentException e) {
      theBoard.tryAddShip(toMove);
      throw e;
    }
  }

  public int getMovesLeft() {
    return movesLeft;
  }
}
