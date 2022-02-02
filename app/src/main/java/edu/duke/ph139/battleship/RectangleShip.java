package edu.duke.ph139.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  final String name;

  /**
   * Constrcuts a set of Coordinates corresponding the RectangleShip with the
   * specified upper left corner Coordinate, width and height.
   * 
   * @param upperLeft is the Coordinate of the RectangleShip's upper left corner.
   * @param width     is the width of the RectangleShip.
   * @param height    is the height of the RectangleShip.
   * @return the set of Coordinates corresponding to the RectangleShip.
   */
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> shipCoordinates = new HashSet<>();
    int row = upperLeft.getRow();
    int col = upperLeft.getColumn();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Coordinate c = new Coordinate(row + j, col + i);
        shipCoordinates.add(c);
      }
    }
    return shipCoordinates;
  }

  /**
   * Constructs a RectangleShip with the specified name, upper left Coordinate,
   * width, height, and ShipDisplayInfo for self and enemy perspective.
   * 
   * @param name             is the name of the ship.
   * @param upperLeft        is the upper left Coordinate of the ship.
   * @param width            is the width of the ship.
   * @param height           is the height of the ship.
   * @param myDisplayInfo    is the display info for self perspective.
   * @param enemyDisplayInfo is the display info for enemy's perspective.
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  /**
   * Constructs a RectangleShip with the specified name, upper left Coordinate,
   * width, height, and display data for the ship.
   * 
   * @param name      is the name of the ship.
   * @param upperLeft is the upper left Coordinate of the ship.
   * @param width     is the width of the ship.
   * @param height    is the height of the ship.
   * @param data      is the display data for the ship.
   * @param onHit     is the display data for the ship when its hit.
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit),
        new SimpleShipDisplayInfo<>(null, data));
  }

  /**
   * Constructs a "testship" with the specified upper left Coordinate and display
   * data.
   */
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

  /**
   * @return the name of the RectangleShip.
   */
  @Override
  public String getName() {
    return name;
  }

}
