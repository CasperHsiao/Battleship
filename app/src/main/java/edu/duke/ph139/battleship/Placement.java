package edu.duke.ph139.battleship;

public class Placement {
  final Coordinate where;
  final char orientation;

  /**
   * Constructs a Placement with the specified Coordinate and orientation.
   *
   * @param where is the Coordinate of the Placement
   * @param orien is the orientation of the Placement.
   */
  public Placement(Coordinate where, char orien) {
    this.where = where;
    orien = Character.toUpperCase(orien);
    this.orientation = orien;
  }

  /**
   * Constructs a Placement with the specified description String. The first
   * character indicates the row position and the second character indicates the
   * column position. Third character indicates the orientation.
   * 
   * @param descr is the String descroption of the Placement.
   * @throws IllegalAccessException if descr's length is not 3, or first character
   *                                is not a alphabet or second character is not a
   *                                digit.
   */
  public Placement(String descr) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("Placement must be 3 characters long but is " + descr.length());
    }
    String coordinate_descr = descr.substring(0, 2);
    if (!Character.isAlphabetic(coordinate_descr.charAt(0))) { // || !Character.isLowerCase(coordinate_descr.charAt(0)))
                                                               // {
      throw new IllegalArgumentException(
          "First character of the placement must be a alphabet but is " + coordinate_descr.charAt(0));
    }
    if (!Character.isDigit(coordinate_descr.charAt(1))) {
      throw new IllegalArgumentException(
          "Second character of the placement must be a digit but is " + coordinate_descr.charAt(1));
    }
    Coordinate coordinate = new Coordinate(coordinate_descr);
    this.where = coordinate;
    char orien = Character.toUpperCase(descr.charAt(2));
    this.orientation = orien;
  }

  /**
   * @return the orientation of the Placement.
   */
  public char getOrientation() {
    return orientation;
  }

  /**
   * @return the Coordinate of the Placement.
   */
  public Coordinate getWhere() {
    return where;
  }

  /**
   * @return the String representation of the Placement.
   */
  @Override
  public String toString() {
    return "Place at coordinate (" + where.getRow() + ", " + where.getColumn() + ") with the " + orientation
        + " orientation.";
  }

  /**
   * @return the hashCode of the Placement.
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Checks the equivalency of this Placement to the other Object.
   * 
   * @param other is the object to check equivalency.
   * @return true if this and other is equivalent. Otherwise, false.
   */
  @Override
  public boolean equals(Object other) {
    if (other.getClass().equals(getClass())) {
      Placement p = (Placement) other;
      return orientation == p.orientation && where.equals(p.where);
    }
    return false;
  }

}
