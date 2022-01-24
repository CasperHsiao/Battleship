package edu.duke.ph139.battleship;

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
    return makeHeader() + makeEmptyBody() + makeHeader();
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

  /**
   * This makes the empty body, e.g. "A | | A\n" + "B | | B\n"
   * 
   * @return the String that is the empty body for the given board
   */
  String makeEmptyBody() {
    StringBuilder ans = new StringBuilder();
    char BaseCharacter = 'A';
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      char rowCharacter = (char) (BaseCharacter + i);
      ans.append(rowCharacter + " ");
      String sep = "";
      for (int j = 0; j < toDisplay.getWidth(); j++) {
        ans.append(sep);
        ans.append(" ");
        sep = "|";
      }
      ans.append(" " + rowCharacter + "\n");
    }
    return ans.toString();
  }

}
