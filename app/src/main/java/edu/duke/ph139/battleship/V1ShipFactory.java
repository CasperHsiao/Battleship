package edu.duke.ph139.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {
  final static Character ON_HIT = '*';

  /**
   * Constructs a RectangleShip with the specified name, Placement, width, height,
   * and display data for the ship.
   * 
   * @param name   is the name of the ship.
   * @param where  is the Placement of the ship.
   * @param width  is the width of the ship.
   * @param height is the height of the ship.
   * @param data   is the display data for the ship.
   */
  protected Ship<Character> createRectangleShip(Placement where, int w, int h, char letter, String name) {
    char orien = where.getOrientation();
    if (orien != 'V' && orien != 'H') {
      throw new IllegalArgumentException("This ship's placement orientation must be (V)ertical or (H)orizontal but is " + orien);
    }
    if (orien == 'H') {
      int temp = w;
      w = h;
      h = temp;
    }
    return new RectangleShip<>(name, where.getWhere(), w, h, letter, ON_HIT);
  }

  /**
   * Make a submarine.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the submarine.
   */
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    int width = 1;
    int height = 2;
    char data = 's';
    String name = "submarine";
    return createRectangleShip(where, width, height, data, name);
  }

  /**
   * Make a battleship.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the battleship.
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    int width = 1;
    int height = 4;
    char data = 'b';
    String name = "battleship";
    return createRectangleShip(where, width, height, data, name);
  }

  /**
   * Make a carrier.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the carrier.
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    int width = 1;
    int height = 6;
    char data = 'c';
    String name = "carrier";
    return createRectangleShip(where, width, height, data, name);
  }

  /**
   * Make a destroyer.
   * 
   * @param where specifies the location and orientation of the ship to make
   * @return the Ship created for the destroyer.
   */
  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    int width = 1;
    int height = 3;
    char data = 'd';
    String name = "destroyer";
    return createRectangleShip(where, width, height, data, name);
  }

}
