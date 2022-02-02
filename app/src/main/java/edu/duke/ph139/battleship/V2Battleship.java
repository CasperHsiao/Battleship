package edu.duke.ph139.battleship;

import java.util.HashSet;

public class V2Battleship<T> extends BasicShip<T> {
  final String name;
  
  static HashSet<Coordinate> makeCoords(Placement where) {
    HashSet<Coordinate> shipCoordinates = new HashSet<>();
    int ULR = where.getWhere().getRow();
    int ULC = where.getWhere().getColumn();
    char orien = where.getOrientation();
    if (orien == 'U') {
      shipCoordinates.add(new Coordinate(ULR, ULC + 1));
      for (int i = 0; i < 3; i++) {
        shipCoordinates.add(new Coordinate(ULR + 1, ULC + i));
      }
    } else if (orien == 'D') {
      shipCoordinates.add(new Coordinate(ULR + 1, ULC + 1));
      for (int i = 0; i < 3; i++) {
        shipCoordinates.add(new Coordinate(ULR, ULC + i));
      }

    } else if (orien == 'R') {
      shipCoordinates.add(new Coordinate(ULR + 1, ULC + 1));
      for (int i = 0; i < 3; i++) {
        shipCoordinates.add(new Coordinate(ULR + i, ULC));
      }
    } else if (orien == 'L') {
      shipCoordinates.add(new Coordinate(ULR + 1, ULC));
      for (int i = 0; i < 3; i++) {
        shipCoordinates.add(new Coordinate(ULR + i, ULC + 1));
      }
    } else {
      throw new IllegalArgumentException(
          "This ship's placement's orientation must be (U)p, (R)ight, (D)own, or (L)eft but is " + orien);
    }
    return shipCoordinates;
  }

  public V2Battleship(String name, Placement where, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(where), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  public V2Battleship(String name, Placement where, T data, T onHit) {
    this(name, where, new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<>(null, data));
  }

  @Override
  public String getName() {
    return name;
  }

}
