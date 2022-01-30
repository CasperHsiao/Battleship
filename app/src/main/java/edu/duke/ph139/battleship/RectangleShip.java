package edu.duke.ph139.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  final private String name;

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

  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit),
        new SimpleShipDisplayInfo<>(null, data));
  }

  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }

  @Override
  public String getName() {
    return name;
  }

}
