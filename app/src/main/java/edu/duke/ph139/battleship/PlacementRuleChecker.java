package edu.duke.ph139.battleship;

public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  /**
   * Checks if the placement of the specified ship on the board satisfies this
   * rule.
   * 
   * @param theShip  is the ship to be placed.
   * @param theBoard is the board in which the ship is placed on.
   * @return the result of the rule checking. Returns null if the placement
   *         satisfies the rule.
   */
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  /**
   * Checks if the placement of the specified ship on the board satisfies all rule
   * chained.
   * 
   * @param theShip  is the ship to be placed.
   * @param theBoard is the board in which the ship is placed on.
   * @return the reult of the rule checking. Returns null if the placement
   *         satisfies all the rules.
   */
  public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    // if we fail our own rule: stop the placement is not legal
    String checkResult = checkMyRule(theShip, theBoard);
    if (checkResult != null) {
      return checkResult;
    }
    // otherwise, ask the rest of the chain
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement is legal
    return null;
  }

}
