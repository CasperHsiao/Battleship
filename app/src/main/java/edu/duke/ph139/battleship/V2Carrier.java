package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class V2Carrier<T> extends AdvancedShip<T> {

  static ArrayList<Coordinate> makeIndexedCoords(Placement where) {
    ArrayList<Coordinate> coordinateIndex = new ArrayList<>();
    int ULR = where.getWhere().getRow();
    int ULC = where.getWhere().getColumn();
    char orien = where.getOrientation();
    if (orien == 'U') {
      for (int i = 0; i < 4; i++) {
        coordinateIndex.add(new Coordinate(ULR + i, ULC));
      }
      for (int i = 0; i < 3; i++) {
        coordinateIndex.add(new Coordinate(ULR + 2 + i, ULC + 1));
      }
    } else if (orien == 'D') {
      for (int i = 0; i < 4; i++) {
        coordinateIndex.add(new Coordinate(ULR + 4 - i, ULC + 1));
      }
      for (int i = 0; i < 3; i++) {
        coordinateIndex.add(new Coordinate(ULR + 2 - i, ULC));
      }      
    } else if (orien == 'R') {
      for (int i = 0; i < 4; i++) {
        coordinateIndex.add(new Coordinate(ULR, ULC + 4 - i));
      }
      for (int i = 0; i < 3; i++) {
        coordinateIndex.add(new Coordinate(ULR + 1, ULC + 2 - i));
      }
    } else if (orien == 'L') {
      for (int i = 0; i < 4; i++) {
        coordinateIndex.add(new Coordinate(ULR + 1, ULC + i));
      }
      for (int i = 0; i < 3; i++) {
        coordinateIndex.add(new Coordinate(ULR, ULC + 2 + i));
      }
    } else {
      throw new IllegalArgumentException(
          "This ship's placement's orientation must be (U)p, (R)ight, (D)own, or (L)eft but is " + orien);
    }
    return coordinateIndex;
  }

  public V2Carrier(String name, Placement where, T data, T onHit) {
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
