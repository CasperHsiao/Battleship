package edu.duke.ph139.battleship;

public class V2ShipFactory extends V1ShipFactory {

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    char data = 'b';
    String name = "Battleship";
    return new V2Battleship<>(name, where, data, ON_HIT);
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    char data = 'c';
    String name = "Carrier";
    return new V2Carrier<>(name, where, data, ON_HIT);
  }

}
