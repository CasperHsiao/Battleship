package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class V2Battleship<T> extends AdvancedShip<T> {
  
  static ArrayList<Coordinate> makeIndexedCoords(Placement where) {
    ArrayList<Coordinate> coordinateIndex = new ArrayList<>();
    int ULR = where.getWhere().getRow();
    int ULC = where.getWhere().getColumn();
    char orien = where.getOrientation();
    if (orien == 'U') {
      Coordinate a = new Coordinate(ULR, ULC + 1);
      Coordinate b = new Coordinate(ULR + 1, ULC);
      Coordinate c = new Coordinate(ULR + 1, ULC + 1);
      Coordinate d = new Coordinate(ULR + 1, ULC + 2);
      coordinateIndex.add(a);
      coordinateIndex.add(b);
      coordinateIndex.add(c);
      coordinateIndex.add(d);
    } else if (orien == 'D') {
      Coordinate a = new Coordinate(ULR + 1, ULC + 1);
      Coordinate b = new Coordinate(ULR, ULC + 2);
      Coordinate c = new Coordinate(ULR, ULC + 1);
      Coordinate d = new Coordinate(ULR, ULC);
      coordinateIndex.add(a);
      coordinateIndex.add(b);
      coordinateIndex.add(c);
      coordinateIndex.add(d);
    } else if (orien == 'R') {
      Coordinate a = new Coordinate(ULR + 1, ULC + 1);
      Coordinate b = new Coordinate(ULR, ULC);
      Coordinate c = new Coordinate(ULR + 1, ULC);
      Coordinate d = new Coordinate(ULR + 2, ULC);
      coordinateIndex.add(a);
      coordinateIndex.add(b);
      coordinateIndex.add(c);
      coordinateIndex.add(d);
    } else if (orien == 'L') {
      Coordinate a = new Coordinate(ULR + 1, ULC);
      Coordinate b = new Coordinate(ULR + 2, ULC + 1);
      Coordinate c = new Coordinate(ULR + 1, ULC + 1);
      Coordinate d = new Coordinate(ULR, ULC + 1);
      coordinateIndex.add(a);
      coordinateIndex.add(b);
      coordinateIndex.add(c);
      coordinateIndex.add(d);
    } else {
      throw new IllegalArgumentException(
          "This ship's placement's orientation must be (U)p, (R)ight, (D)own, or (L)eft but is " + orien);
    }
    return coordinateIndex;
  }

  public V2Battleship(String name, Placement where, T data, T onHit) {
    super(name, where, data, onHit, makeIndexedCoords(where));
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
