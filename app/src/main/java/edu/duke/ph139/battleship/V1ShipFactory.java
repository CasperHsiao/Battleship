package edu.duke.ph139.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {
  final static Character ON_HIT = '*';

  protected Ship<Character> createRectangleShip(Placement where, int w, int h, char letter, String name) {
    char orien = where.getOrientation();
    if (orien != 'V' && orien != 'H') {
      throw new IllegalArgumentException("Placement's orientation must be (V)ertical or (H)orizontal but is " + orien);
    }
    if (orien == 'H') {
      int temp = w;
      w = h;
      h = temp;
    }
    return new RectangleShip<>(name, where.getWhere(), w, h, letter, ON_HIT);
  }
  
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    int width = 1;
    int height = 2;
    char data = 's';
    String name = "submarine";
    return createRectangleShip(where, width, height, data, name);
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    int width = 1;
    int height = 4;
    char data = 'b';
    String name = "battleship";
    return createRectangleShip(where, width, height, data, name);
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    int width = 1;
    int height = 6;
    char data = 'c';
    String name = "carrier";
    return createRectangleShip(where, width, height, data, name);
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    int width = 1;
    int height = 3;
    char data = 'd';
    String name = "destroyer";
    return createRectangleShip(where, width, height, data, name);
  }

}
