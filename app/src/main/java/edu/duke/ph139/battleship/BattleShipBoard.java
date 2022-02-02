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
   * Constructs a BattleShipBoard with a dimension of the specified width and
   * height. The default rule checker of this board includes in bounds and no
   * collision rule.
   * 
   * @param w        is the width of the newly constructed board.
   * @param h        is the height of the newly constructed board.
   * @param missInfo is the display info of a missed shot indication.
   * @throws IllegalArgumentException if the width or height is less than or equal
   *                                  to zero.
   */
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null)), missInfo);
  }

  /**
   * Constructs a BattleShipBoard with a dimension of the specified width and
   * height.
   * 
   * @param w                is the width of the newly constructed board.
   * @param h                is the height of the newly constructed board.
   * @param placementChecker is the rule checker object of this board.
   * @param missInfo         is the display info of a missed shot indication.
   * @throws IllegalArgumentException if the width or height is less than or equal
   *                                  to zero.
   */
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

  /**
   * Checks if this BattleShipBoard has lost. Returns true if all the ships on
   * this board has sunk, which is the losing condition. Otherwise, returns false.
   * 
   * @return if the board has lost.
   */
  public boolean hasLost() {
    for (Ship<T> s : myShips) {
      if (!s.isSunk()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the specified Coordinate is inside the Board.
   * 
   * @param c is the Coordinate to be checked.
   * @return the result of the check. Returns null if the Coordinate is inside the
   *         bounds.
   */
  public String checkCoordinateInBounds(Coordinate c) {
    if (c.getRow() < 0 || c.getRow() >= height) {
      return "The coordinate entered is out of bounds.";
    }
    if (c.getColumn() < 0 || c.getColumn() >= width) {
      return "The coordinate entered is out of bounds.";
    }
    return null;
  }

  /**
   * Fires/attacks at the specified Coordinate. Returns the Ship object if one is
   * hit at the specified Coordinate. Otherwise, return null.
   * 
   * @param c is the Coordinate to fire/attack at.
   */
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

  /**
   * Attemps to add the specified Ship onto the BattleShipBoard. Returns null if
   * the ship is successfully added. Otherwise, the specified Ship violates the
   * placementChecker and returns a descriptive error message.
   * 
   * @param toAdd is the Ship to add to the board.
   * @return the result of adding the Ship
   */
  public String tryAddShip(Ship<T> toAdd) {
    String placementResult = placementChecker.checkPlacement(toAdd, this);
    if (placementResult == null) {
      myShips.add(toAdd);
      return null;
    }
    return placementResult;
  }

  /**
   * Checks what is at the specified Coordinate and returns the appropriate Ship
   * display info according to the specified perspective (self or enemy).
   * Otherwise, returns null if there is no Ship at the specified Coordinate.
   * 
   * @param where  is the coordinate to check the display info at.
   * @param isSelf is the self or enemy perspective indication.
   * @return the display info.
   */
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    return null;
  }

  /**
   * Checks what display info is at the specified Coordinate in the enemy's
   * perspective.
   * 
   * @param where is the coordinate to check the display info at.
   * @return the display info for the enemy's perspective
   */
  public T whatIsAtForEnemy(Coordinate where) {
    if (enemyMisses.contains(where)) {
      return missInfo;
    }
    return whatIsAt(where, false);
  }

  /**
   * Checks what display info is at the specified Coordinate in the self
   * perspective.
   * 
   * @param where is the coordinate to check the display info at.
   * @return the display info for the self perspective
   */
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  /**
   * @return the width of the BattleShipBoard.
   */
  public int getWidth() {
    return width;
  }

  /**
   * @return the height of the BattleShipBoard..
   */
  public int getHeight() {
    return height;
  }
}
