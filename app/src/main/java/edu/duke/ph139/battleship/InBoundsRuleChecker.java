package edu.duke.ph139.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  /**
   * Constructs a InBoundsRuleChecker with the next rule specified.
   * 
   * @param next is the next placement rule checker to chain
   */
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   * Checks if the placement of the specified ship on the board satisfies this in
   * bounds rule.
   * 
   * @param theShip  is the ship to be placed.
   * @param theBoard is the board in which the ship is placed on.
   * @return the result of the rule checking. Returns null if the placement
   *         satisfies the rule.
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      int row = c.getRow();
      int col = c.getColumn();
      if (row < 0) {
        return "That placement is invalid: the ship goes off the top of the board.";
      }
      if (row >= theBoard.getHeight()) {
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }
      if (col < 0) {
        return "That placement is invalid: the ship goes off the left of the board.";
      }
      if (col >= theBoard.getWidth()) {
        return "That placement is invalid: the ship goes off the right of the board.";
      }
    }
    return null;
  }

}
