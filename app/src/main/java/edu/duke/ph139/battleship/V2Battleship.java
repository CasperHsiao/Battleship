package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class V2Battleship<T> extends BasicShip<T> {
  final String name;

  /**
   * Constructs the indexed coordinate of the battleship according to its
   * placement.
   * 
   * @param where is the placement of the ship.
   * @return the array of indexed coordinates.
   */
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

  /**
   * Constructs the V2Battleship.
   * 
   * @param name  is the name of the ship.
   * @param data  is the display data for the ship.
   * @param onHit is the display data for the ship when its hit.
   * @param where is the placement of the ship.
   */
  public V2Battleship(String name, Placement where, T data, T onHit) {
    super(new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<>(null, data), makeIndexedCoords(where));
    this.name = name;
  }

  /**
   * @return the name of the RectangleShip.
   */
  @Override
  public String getName() {
    return name;
  }
}
