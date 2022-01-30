package edu.duke.ph139.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of a Board (i.e., converting it to a
 * string to show to the user). It supports two ways to display the Board: one
 * for the player's own board, and one for the enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display.
   * @throws IllegalArgumentException if the board is larger than 10x26.
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * This makes the empty board view for the given boardtoDisplay
   * 
   * @return the String that is the empty board view for the given board
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    StringBuilder ans = new StringBuilder();
    char BaseCharacter = 'A';
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      char rowCharacter = (char) (BaseCharacter + i);
      ans.append(rowCharacter + " ");
      String sep = "";
      for (int j = 0; j < toDisplay.getWidth(); j++) {
        ans.append(sep);
        Coordinate c = new Coordinate(i, j);
        Character info = getSquareFn.apply(c);
        if (info != null) {
          ans.append(info);
        } else {
          ans.append(" ");
        }
        sep = "|";
      }
      ans.append(" " + rowCharacter + "\n");
    }
    return makeHeader() + ans.toString() + makeHeader();
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at start
    String sep = "";
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }


}
