package edu.duke.ph139.battleship;

import java.util.ArrayList;

public class V2ShipFactory extends V1ShipFactory {

  /*
  @Override
  protected Ship<Character> createRectangleShip(Placement where, int w, int h, char letter, String name) {
    char orien = where.getOrientation();
    if (orien != 'V' && orien != 'H') {
      throw new IllegalArgumentException(
          "This ship's placement orientation must be (V)ertical or (H)orizontal but is " + orien);
    }
    ArrayList<Coordinate> coordinateIndex = new ArrayList<>();
    int ULR = where.getWhere().getRow();
    int ULC = where.getWhere().getColumn();
    for (int i = 0; i < h; i++) {
      Coordinate c;
      if (orien == 'H') {
        c = new Coordinate(ULR, ULC + i);
      } else {
        c = new Coordinate(ULR + i, ULC);
      }
      coordinateIndex.add(c);
    }
    return new RectangleShip<Character>(name, where, letter, ON_HIT, coordinateIndex);
  }
  */

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    char data = 'b';
    String name = "battleship";
    return new V2Battleship<>(name, where, data, ON_HIT);
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    char data = 'c';
    String name = "carrier";
    return new V2Carrier<>(name, where, data, ON_HIT);
  }

}
