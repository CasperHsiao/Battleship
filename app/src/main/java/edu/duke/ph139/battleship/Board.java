package edu.duke.ph139.battleship;

public interface Board<T>{
  public int getWidth();
  public int getHeight();
  public T whatIsAt(Coordinate where);
  public boolean tryAddShip(Ship<T> toAdd);
}
