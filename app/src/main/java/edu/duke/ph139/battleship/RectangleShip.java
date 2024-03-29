package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class RectangleShip<T> extends BasicShip<T> {
  final String name; 


  /**
   * Constructs the indexed coordinate of the battleship according to its
   * placement.
   * 
   * @param where is the placement of the ship.
   * @return the array of indexed coordinates.
   */
  static ArrayList<Coordinate> makeIndexedCoords(Coordinate upperLeft, int width, int height) {
    ArrayList<Coordinate> coordinateIndex = new ArrayList<>();
    int ULR = upperLeft.getRow();
    int ULC = upperLeft.getColumn();
    if (width >= height) { //square or horizontal oriented
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          coordinateIndex.add(new Coordinate(ULR + i, ULC + j));
        }
      }
    } else {
      for (int j = 0; j < width; j++) {
        for (int i = 0; i < height; i++) {
          coordinateIndex.add(new Coordinate(ULR + i, ULC + width - 1 - j));
        }
      }
    }
    return coordinateIndex;
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
    super(myDisplayInfo, enemyDisplayInfo, makeIndexedCoords(upperLeft, width, height));
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
    super(new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<>(null, data), makeIndexedCoords(upperLeft, width, height));
    this.name = name;
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
