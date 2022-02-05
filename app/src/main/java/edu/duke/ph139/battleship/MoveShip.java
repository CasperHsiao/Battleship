package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class MoveShip<T> {
  int movesLeft;

  /**
   * Constructs a MoveShip action object for the TextPlayer.
   * 
   * @param n is the number of moves for this action.
   */
  public MoveShip(int n) {
    this.movesLeft = n;
  }

  /**
   * Translates the hit coordinate of the old ship to the new ship.
   * 
   * @param toMove  is the old ship.
   * @param newShip is the new ship.
   */
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

  /**
   * Executes the move ship action.
   * 
   * @param theBoard is the board that the ship to move is on.
   * @param toMove   is the ship to move.
   * @param newShip  is the new ship placement.
   * @throws IllegalArgumentException if the ship placement on the board is
   *                                  invalid.
   */
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

  /**
   * @return the number of moves left.
   */
  public int getMovesLeft() {
    return movesLeft;
  }
}
