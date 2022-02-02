package edu.duke.ph139.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private final T myData;
  private final T onHit;

  /**
   * Constructs a SimpleShipDisplayInfo with the specified display data.
   * 
   * @param myData is the display data for the ship.
   * @param onHit  is the display data for the ship on hit.
   */
  public SimpleShipDisplayInfo(T myData, T onHit) {
    this.myData = myData;
    this.onHit = onHit;
  }

  /**
   * Gets the display data for the ship according to the specified Coordinate and
   * if it was hit.
   * 
   * @param where is the Coordinate to get the display data at.
   * @param hit   indicates whether that Coordinate was hit.
   * @return the display data of the ship.
   */
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if (hit) {
      return onHit;
    }
    return myData;
  }

}
