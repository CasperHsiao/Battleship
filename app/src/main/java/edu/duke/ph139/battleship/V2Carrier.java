package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class V2Carrier<T> extends BasicShip<T> {
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

  /**
   * Constructs the V2Battleship.
   * 
   * @param name  is the name of the ship.
   * @param data  is the display data for the ship.
   * @param onHit is the display data for the ship when its hit.
   * @param where is the placement of the ship.
   */
  public V2Carrier(String name, Placement where, T data, T onHit) {
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
