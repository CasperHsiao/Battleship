package edu.duke.ph139.battleship;

public interface Board<T>{
  public int getWidth();
  public int getHeight();
  public T whatIsAtForSelf(Coordinate where);
  public String tryAddShip(Ship<T> toAdd);
  public String checkCoordinateInBounds(Coordinate c);
  public Ship<T> fireAt(Coordinate c);
  public Ship<T> removeShip(Coordinate c);
  public T whatIsAtForEnemy(Coordinate where);
  public boolean allSunk();
}
