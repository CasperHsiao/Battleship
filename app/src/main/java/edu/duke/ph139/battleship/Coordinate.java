package edu.duke.ph139.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  public Coordinate(String descr) {
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Coordinate must be 2 characters long but is " + descr.length());
    }
    descr.toUpperCase();
    char rowLetter = descr.charAt(0);
    char colLetter = descr.charAt(1);
    if (rowLetter < 'A' || rowLetter > 'Z') {
      throw new IllegalArgumentException("Coordinate's row must be a character between A and Z but is " + rowLetter);
    }
    if (colLetter < '0' || colLetter > '9') {
      throw new IllegalArgumentException("Coordinate's column must be a number between 0 and 9 but is " + colLetter);
    }
    this.row = rowLetter - 'A';
    this.column = colLetter - '0';
  }

  public Coordinate(int r, int c) {
    if (r < 0 || r > 25) {
      throw new IllegalArgumentException("Coordinate's row must be a number between 0 and 25 but is " + r);
    }
    if (c < 0 || c > 9) {
      throw new IllegalArgumentException("Coordinate's column must be a number between 0 and 9 but is " + c);
    }
    this.row = r;
    this.column = c;
  }

  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }

  @Override
  public boolean equals(Object other) {
    if (other.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) other;
      return row == c.row && column == c.column;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }

}
