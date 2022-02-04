package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class ShipAllocator<T> {

  public void placeShip(Ship<T> ship, Board<T> theBoard) {
    String addShipResult = theBoard.tryAddShip(ship);
    if (addShipResult != null) {
      throw new IllegalArgumentException(addShipResult);
    }
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

  public void moveShip(Board<T> theBoard, Ship<T> toMove, Ship<T> newShip) {
    theBoard.removeShip(toMove);
    try {
      placeShip(newShip, theBoard);
      shipTranslation(toMove, newShip);
    } catch (IllegalArgumentException e) {
      theBoard.tryAddShip(toMove);
      throw e;
    }
  }

}
