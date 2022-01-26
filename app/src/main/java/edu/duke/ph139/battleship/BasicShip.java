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

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.get(where) != null;
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    //TODO this is not right. We need to look up the hit status
    return myDisplayInfo.getInfo(where, false);
  }

}
