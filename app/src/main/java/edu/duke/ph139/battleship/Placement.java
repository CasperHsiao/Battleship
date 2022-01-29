package edu.duke.ph139.battleship;

public class Placement {
  final Coordinate where;
  final char orientation;

  public Placement(Coordinate where, char orien) {
    this.where = where;
    orien = Character.toUpperCase(orien);
    this.orientation = orien;
  }

  public Placement(String descr) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("Placement must be 3 characters long but is " + descr.length());
    }    
    String coordinate_descr = descr.substring(0, 2);
    if (!Character.isAlphabetic(coordinate_descr.charAt(0))){ // || !Character.isLowerCase(coordinate_descr.charAt(0))) {
      throw new IllegalArgumentException("First character of the placement must be a alphabet but is " + coordinate_descr.charAt(0));
    }
    if (!Character.isDigit(coordinate_descr.charAt(1))) {
      throw new IllegalArgumentException("Second character of the placement must be a digit but is " + coordinate_descr.charAt(1));
    }
    Coordinate coordinate = new Coordinate(coordinate_descr);
    this.where = coordinate;
    char orien = Character.toUpperCase(descr.charAt(2));
    this.orientation = orien;
  }

  public char getOrientation() {
    return orientation;
  }

  public Coordinate getWhere() {
    return where;
  }

  @Override
  public String toString() {
    return "Place at coordinate (" + where.getRow() + ", " + where.getColumn() + ") with the " + orientation
        + " orientation.";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other.getClass().equals(getClass())) {
      Placement p = (Placement) other;
      return orientation == p.orientation && where.equals(p.where);
    }
    return false;
  }

}
