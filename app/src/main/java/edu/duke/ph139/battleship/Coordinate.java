package edu.duke.ph139.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  /**
   * Constructs a Coordinate with the specified description String. The first
   * character indicates the vertical position and the second character indicates
   * the horizontal position.
   * 
   * @param descr is the String descroption of the Coordinate.
   * @throws IllegalAccessException if descr's length is not 2.
   */
  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Coordinate must be 2 characters long but is " + descr.length());
    }
    descr = descr.toUpperCase();
    char rowLetter = descr.charAt(0);
    char colLetter = descr.charAt(1);

    /*
    if (!Character.isAlphabetic(rowLetter)) {
      throw new IllegalArgumentException("First character of the coordinate must be a alphabet but is " + rowLetter);
    }

    if (!Character.isDigit(colLetter)) {
      throw new IllegalArgumentException("First character of the coordinate must be a digit but is " + colLetter);
    }
    */
    
    this.row = rowLetter - 'A';
    this.column = colLetter - '0';
  }

  /**
   * Constructs a Coordinate with the specified row and column.
   * 
   * @param r is the row coordinate.
   * @param c is the column coordinate.
   */
  public Coordinate(int r, int c) {
    /*
     * if (r < 0) {
     * IllegalArgumentException("Coordinate's row must be a number between 0 and 25 but is "
     * + r); } if (c < 0) {
     * IllegalArgumentException("Coordinate's column must be a number between 0 and 9 but is "
     * + c); }
     */
    this.row = r;
    this.column = c;
  }

  /**
   * @return the column of the Coordinate.
   */
  public int getColumn() {
    return column;
  }

  /**
   * @return the column of the Coordinate.
   */
  public int getRow() {
    return row;
  }

  /**
   * Checks the equivalency of this Coordinate to the other Object.
   * 
   * @param other is the object to check equivalency.
   * @return true if this and other is equivalent. Otherwise, false.
   */
  @Override
  public boolean equals(Object other) {
    if (other.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) other;
      return row == c.row && column == c.column;
    }
    return false;
  }

  /**
   * @return the hashCode of the Coordinate.
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * @return the String representation of the Coordinate.
   */
  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }

}
