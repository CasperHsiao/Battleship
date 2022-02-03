package edu.duke.ph139.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class V2Battleship<T> extends BasicShip<T> {
  final String name;
  
  static ArrayList<Coordinate> makeCoords(Placement where) {
    HashSet<Coordinate> shipCoordinates = new HashSet<>();
    ArrayList<Coordinate> coordinateIndex = new ArrayList<>();
    int ULR = where.getWhere().getRow();
    int ULC = where.getWhere().getColumn();
    char orien = where.getOrientation();
    if (orien == 'U') {
      Coordinate a = new Coordinate(ULR, ULC + 1);
      Coordinate b = new Coordinate(ULR + 1, ULC);
      Coordinate c = new Coordinate(ULR + 1, ULC + 1);
      Coordinate d = new Coordinate(ULR + 1, ULC + 2);
      shipCoordinates.add(a);
      coordinateIndex.add(a);
      shipCoordinates.add(b);
      coordinateIndex.add(b);
      shipCoordinates.add(c);
      coordinateIndex.add(c);
      shipCoordinates.add(d);
      coordinateIndex.add(d);
    } else if (orien == 'D') {
      Coordinate a = new Coordinate(ULR + 1, ULC + 1);
      Coordinate b = new Coordinate(ULR, ULC + 2);
      Coordinate c = new Coordinate(ULR, ULC + 1);
      Coordinate d = new Coordinate(ULR, ULC);
      shipCoordinates.add(a);
      coordinateIndex.add(a);
      shipCoordinates.add(b);
      coordinateIndex.add(b);
      shipCoordinates.add(c);
      coordinateIndex.add(c);
      shipCoordinates.add(d);
      coordinateIndex.add(d);
    } else if (orien == 'R') {
      Coordinate a = new Coordinate(ULR + 1, ULC + 1);
      Coordinate b = new Coordinate(ULR, ULC);
      Coordinate c = new Coordinate(ULR + 1, ULC);
      Coordinate d = new Coordinate(ULR + 2, ULC);
      shipCoordinates.add(a);
      coordinateIndex.add(a);
      shipCoordinates.add(b);
      coordinateIndex.add(b);
      shipCoordinates.add(c);
      coordinateIndex.add(c);
      shipCoordinates.add(d);
      coordinateIndex.add(d);

    } else if (orien == 'L') {
      Coordinate a = new Coordinate(ULR + 1, ULC);
      Coordinate b = new Coordinate(ULR + 2, ULC + 1);
      Coordinate c = new Coordinate(ULR + 1, ULC + 1);
      Coordinate d = new Coordinate(ULR, ULC + 1);
      shipCoordinates.add(a);
      coordinateIndex.add(a);
      shipCoordinates.add(b);
      coordinateIndex.add(b);
      shipCoordinates.add(c);
      coordinateIndex.add(c);
      shipCoordinates.add(d);
      coordinateIndex.add(d);      
    } else {
      throw new IllegalArgumentException(
          "This ship's placement's orientation must be (U)p, (R)ight, (D)own, or (L)eft but is " + orien);
    }
    return coordinateIndex;
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

  /*
  @Override
  public Coordinate getNewPlacementCoordinate(Coordinate c, Placement newPlacement) {
    Coordinate newUpperLeft = newPlacement.getWhere();
    Coordinate oldUpperLeft = p.getWhere();
    int relRowDist = c.getRow() - oldUpperLeft.getRow();
    int relColDist = c.getColumn() - oldUpperLeft.getColumn();
    if (newPlacement.getOrientation() == this.p.getOrientation()) {
      return new Coordinate(newUpperLeft.getRow() + relRowDist, newUpperLeft.getColumn() + relColDist);
    }

    return null;
  }
  */
}
