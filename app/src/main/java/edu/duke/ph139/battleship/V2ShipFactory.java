package edu.duke.ph139.battleship;

public class V2ShipFactory extends V1ShipFactory {

  /** 
   * Makes the V2Battleship.
   * @param where is the ship placement.
   * @returns the ship made.
   */
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    char data = 'b';
    String name = "Battleship";
    return new V2Battleship<>(name, where, data, ON_HIT);
  }

  /** 
   * Makes the V2Carrier.
   * @param where is the ship placement.
   * @returns the ship made.
   */
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    char data = 'c';
    String name = "Carrier";
    return new V2Carrier<>(name, where, data, ON_HIT);
  }

}
