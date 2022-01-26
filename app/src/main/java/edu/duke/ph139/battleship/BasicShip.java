package edu.duke.ph139.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected HashMap<Coordinate, Boolean> myPieces;

  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    this.myPieces = new HashMap<>();
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
  }

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (myPieces.containsKey(c) == false) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.get(where) != null;
  }

  @Override
  public boolean isSunk() {
    for (boolean wasHit : myPieces.values()) {
      if (wasHit == false) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.replace(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    return myDisplayInfo.getInfo(where, wasHitAt(where));
  }

}
