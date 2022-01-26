package edu.duke.ph139.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> shipCoordinates = new HashSet<>();
    int row = upperLeft.getRow();
    int col = upperLeft.getColumn();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        Coordinate c = new Coordinate(row+j, col+i);
        shipCoordinates.add(c);
      }
    }
    return shipCoordinates;
  }

  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    super(makeCoords(upperLeft, width, height), new SimpleShipDisplayInfo<T>(data, onHit));
  }

  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft, 1, 1, data, onHit);
  }

}
