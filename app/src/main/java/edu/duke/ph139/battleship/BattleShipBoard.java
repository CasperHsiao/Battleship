package edu.duke.ph139.battleship;

public class BattleShipBoard implements Board {
  private final int width;
  private final int height;

  /**
   * Constructs a BattleShipBoard with the specified width and height
   * 
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height is less than or equal
   *                                  to zero.
   */
  public BattleShipBoard(int w, int h) {
    if (w <= 0) {
      throw new IllegalArgumentException("BatlleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BatlleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
