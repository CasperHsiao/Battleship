package edu.duke.ph139.battleship;

import java.util.HashSet;

public class V2Carrier<T> extends BasicShip<T> {
  final String name;

  static HashSet<Coordinate> makeCoords(Placement where) {
    HashSet<Coordinate> shipCoordinates = new HashSet<>();
    int ULR = where.getWhere().getRow();
    int ULC = where.getWhere().getColumn();
    char orien = where.getOrientation();
    if (orien == 'U') {
      for (int i = 0; i < 3; i++) {
        shipCoordinates.add(new Coordinate(ULR + 2 + i, ULC + 1));
      }
      for (int i = 0; i < 4; i++) {
        shipCoordinates.add(new Coordinate(ULR + i, ULC));
      }
    } else if (orien == 'D') {
      for (int i = 0; i < 3; i++) {
        shipCoordinates.add(new Coordinate(ULR + i, ULC));
      }
      for (int i = 0; i < 4; i++) {
        shipCoordinates.add(new Coordinate(ULR + 1 + i, ULC + 1));
      }
    } else if (orien == 'R') {
      for (int i = 0; i < 3; i++) {
        shipCoordinates.add(new Coordinate(ULR + 1, ULC + i));
      }
      for (int i = 0; i < 4; i++) {
        shipCoordinates.add(new Coordinate(ULR, ULC + 1 + i));
      }
    } else if (orien == 'L') {
      for (int i = 0; i < 3; i++) {
        shipCoordinates.add(new Coordinate(ULR, ULC + 2 + i));
      }
      for (int i = 0; i < 4; i++) {
        shipCoordinates.add(new Coordinate(ULR + 1, ULC + i));
      }
    } else {
      throw new IllegalArgumentException(
          "This ship's placement's orientation must be (U)p, (R)ight, (D)own, or (L)eft but is " + orien);
    }
    return shipCoordinates;
  }

  public V2Carrier(String name, Placement where, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(where), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  public V2Carrier(String name, Placement where, T data, T onHit) {
    this(name, where, new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<>(null, data));
  }

  @Override
  public String getName() {
    return this.name;

  }

}
