package edu.duke.ph139.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {


  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (Coordinate c : theShip.getCoordinates()) {
      int row = c.getRow();
      int col = c.getColumn();
      if (row < 0 || row >= theBoard.getHeight()) {
        return false;
      }
      if (col < 0 || col >= theBoard.getWidth()) {
        return false;
      }
    }
    return true;
  }

  

}
