package edu.duke.ph139.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  final T missInfo;

  /**
   * Constructs a BattleShipBoard with the specified width and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height is less than or equal
   *                                  to zero.
   */
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null)), missInfo);
  }

  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
    if (w <= 0) {
      throw new IllegalArgumentException("BatlleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BatlleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<>();
    this.placementChecker = placementChecker;
    this.enemyMisses = new HashSet<>();
    this.missInfo = missInfo;
  }

  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;
  }

  public String tryAddShip(Ship<T> toAdd) {
    String placementResult = placementChecker.checkPlacement(toAdd, this);
    if (placementResult == null) {
      myShips.add(toAdd);
      return null;
    }
    return placementResult;
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    return null;

  }

  public T whatIsAtForEnemy(Coordinate where) {
    if (enemyMisses.contains(where)) {
      return missInfo;
    }
    return whatIsAt(where, false);
  }

  // Does not check if coordinate out of bounds
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
