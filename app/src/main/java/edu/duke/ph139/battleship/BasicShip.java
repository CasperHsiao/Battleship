package edu.duke.ph139.battleship;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  protected ShipDisplayInfo<T> enemyDisplayInfo;
  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ArrayList<Coordinate> coordinateIndex;
  protected HashMap<Coordinate, Boolean> myPieces;

  /**
   * Constructs a BasicShip with the specified collection of Coordinates, self
   * display info and enemy display info of the ship.
   */
  public BasicShip(ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo,
      ArrayList<Coordinate> coordinateIndex) {
    this.myPieces = new HashMap<>();
    for (Coordinate c : coordinateIndex) {
      this.myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    this.coordinateIndex = coordinateIndex;
  }

  /**
   * Checks if the specified Coordinate is contained in this BasicShip.
   * 
   * @param c is the specified Coordinate to check.
   * @throws IllegalArgumentException if the Coordinate is not in this Shio.
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (occupiesCoordinates(c) == false) {
      throw new IllegalArgumentException("This ship does not occupy the given coordinate.");
    }
  }

  /**
   * Check if this ship occupies the given coordinate.
   * 
   * @param where is the Coordinate to check if this Ship occupies
   * @return true if where is inside this ship, false if not.
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.get(where) != null;
  }

  /**
   * Check if this ship has been hit in all of its locations meaning it has been
   * sunk.
   * 
   * @return true if this ship has been sunk, false otherwise.
   */
  @Override
  public boolean isSunk() {
    for (boolean wasHit : myPieces.values()) {
      if (wasHit == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * Make this ship record that it has been hit at the given coordinate. The
   * specified coordinate must be part of the ship.
   * 
   * @param where specifies the coordinates that were hit.
   * @throws IllegalArgumentException if where is not part of the Ship
   */
  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.replace(where, true);
  }

  /**
   * Check if this ship was hit at the specified coordinates. The coordinates must
   * be part of this Ship.
   * 
   * @param where is the coordinates to check.
   * @return true if this ship as hit at the indicated coordinates, and false
   *         otherwise.
   * @throws IllegalArgumentException if the coordinates are not part of this
   *                                  ship.
   */
  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  /**
   * Return the view-specific information at the given coordinate. This coordinate
   * must be part of the ship.
   * 
   * @param where is the coordinate to return information for
   * @throws IllegalArgumentException if where is not part of the Ship
   * @return The view-specific information at that coordinate.
   */
  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    checkCoordinateInThisShip(where);
    if (myShip) {
      return myDisplayInfo.getInfo(where, wasHitAt(where));
    }
    return enemyDisplayInfo.getInfo(where, wasHitAt(where));
  }

  /**
   * Get all of the Coordinates that this Ship occupies.
   * 
   * @return An Iterable with the coordinates that this Ship occupies
   */
  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }

  /**
   * @param idx is the coordinate index to check.
   * @returns the coordinate of the ship according to the given index.
   * @throws IllegalArgumentException if the index is larger than the number of
   *                                  ship coordinates.
   */
  @Override
  public Coordinate getShipCoordinateByIndex(int idx) {
    if (idx >= coordinateIndex.size()) {
      throw new IllegalArgumentException("The requested index does not exist on this ship.");
    }
    return coordinateIndex.get(idx);
  }

  /**
   * @param coordinate is the coordinate to check the index.
   * @returns the index of the given coordinate.
   * @throws IllegalArgumentException if the coordinate is not occupied by this
   *                                  ship.
   */
  @Override
  public int getIndexOfShipCoordinate(Coordinate c) {
    checkCoordinateInThisShip(c);
    return coordinateIndex.indexOf(c);
  }
}
