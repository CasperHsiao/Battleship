package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class AdvancedShip<T> extends BasicShip<T> {
  final String name;
  final ArrayList<Coordinate> coordinateIndex;

  public AdvancedShip(String name, T data, T onHit, ArrayList<Coordinate> coordinateIndex) {
    super(coordinateIndex, new SimpleShipDisplayInfo<>(data, onHit),
        new SimpleShipDisplayInfo<>(null, data));
    this.name = name;
    this.coordinateIndex = coordinateIndex;
  }

  public AdvancedShip(String name,ShipDisplayInfo<T> myDisplayInfo,                      ShipDisplayInfo<T> enemyDisplayInfo, ArrayList<Coordinate> coordinateIndex) {
    super(coordinateIndex, myDisplayInfo, enemyDisplayInfo);
    this.name = name;
    this.coordinateIndex = coordinateIndex;
  }

  public Coordinate getShipCoordinateByIndex(int idx) {
    if (idx >= coordinateIndex.size()) {
      throw new IllegalArgumentException("The requested index does not exist on this ship.");
    }
    return coordinateIndex.get(idx);
  }

  public int getIndexOfShipCoordinate(Coordinate c) {
    checkCoordinateInThisShip(c);
    return coordinateIndex.indexOf(c);
  }

  @Override
  public String getName() {
    return this.name;
  }

}
