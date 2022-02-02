package edu.duke.ph139.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  /**
   * Constructs a NoCollisionRuleChecker with the next rule specified.
   * 
   * @param next is the next placement rule checker to chain
   */
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * Checks if the placement of the specified ship on the board satisfies this no
   * collision rule.
   * 
   * @param theShip  is the ship to be placed.
   * @param theBoard is the board in which the ship is placed on.
   * @return the result of the rule checking. Returns null if the placement
   *         satisfies the rule.
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> myPieces = theShip.getCoordinates();
    for (Coordinate c : myPieces) {
      if (theBoard.whatIsAtForSelf(c) != null) {
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return null;
  }

}
