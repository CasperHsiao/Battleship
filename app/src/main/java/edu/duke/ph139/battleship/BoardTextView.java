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
   * Constructs a BoardTextView with the specified Board to display.
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
   * Constructs the String for the display of self BoardTextView with the enemy's
   * BoardTextView next to it.
   * 
   * @param enemyView   is the enemy's BoardtextView object.
   * @param myHeader    is the Sting to display above self board view.
   * @param enemyHeader is the String to display above enemy's board view.
   * @return the String for the display
   */
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    StringBuilder sb = new StringBuilder();
    int w = toDisplay.getWidth();
    sb.append(" ".repeat(2 * w + 22 - myHeader.length()));
    sb.insert(5, myHeader);
    sb.append(enemyHeader + "\n");
    String own = displayMyOwnBoard();
    String enemy = enemyView.displayEnemyBoard();
    String[] ownLines = own.split("\n");
    String[] enemyLines = enemy.split("\n");
    for (int i = 0; i < ownLines.length; i++) {
      sb.append(ownLines[i]);
      sb.append(" ".repeat(2 * w + 19 - ownLines[i].length()));
      sb.append(enemyLines[i] + "\n");
    }
    return sb.toString();
  }

  /**
   * Constructs the String for the display of this BoardTextView in the self
   * perspective.
   * 
   * @return the String for the display.
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  /**
   * Constructs the String for the display of this BoardTextView in the enemy's
   * perspective.
   * 
   * @return the String for the display.
   */
  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * Constructs the String for the display of this BoardTextView with the
   * specified function, which indicates the persepctive (self or enemy).
   * 
   * @return the String for the display.
   */
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
   * Constructs the String for the header display of this BoardTextView, e.g.
   * 0|1|2|3|4\n.
   * 
   * @return the String that is the header line for this board.
   */
  public String makeHeader() {
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
