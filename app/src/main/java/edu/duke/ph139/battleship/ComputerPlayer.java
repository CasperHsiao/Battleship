package edu.duke.ph139.battleship;

import java.io.PrintStream;
import java.util.ArrayList;

public class ComputerPlayer extends TextPlayer {
  final ArrayList<String> placementCommands;
  int fireAtRow;
  int fireAtColumn;
  
  public ComputerPlayer(Board<Character> theBoard, PrintStream out, AbstractShipFactory<Character> shipFactory, String name) {
    super(theBoard, null, out, shipFactory, name);
    this.placementCommands = new ArrayList<>();
    this.fireAtRow = 0;
    this.fireAtColumn = 0;
    setupPlacementCommands();
  }

  protected void setupPlacementCommands() {
    // Submarine
    this.placementCommands.add("a0h");
    this.placementCommands.add("b0h");
    // Destroyer
    this.placementCommands.add("a2h");
    this.placementCommands.add("b2h");
    this.placementCommands.add("a5h");
    // Battleship
    this.placementCommands.add("c0u");
    this.placementCommands.add("c3u");
    this.placementCommands.add("c6u");
    // Carrier
    this.placementCommands.add("e0u");
    this.placementCommands.add("e2u");
  }

  @Override
  public void doPlacementPhase() {
    for (int i = 0; i < shipToPlace.size(); i++) {
      Placement p = new Placement(placementCommands.get(i));
      String shipName = shipToPlace.get(i);
      Ship<Character> s = shipCreationsFns.get(shipName).apply(p);
      theBoard.tryAddShip(s);
    }
  }

  @Override
  public void doAttackingPhase(Board<Character> enemyBoard, BoardTextView enemyBoardView, String enemyHeader) {
    int h = enemyBoard.getHeight();
    int w = enemyBoard.getWidth();
    Coordinate coord;
    if (fireAtColumn >= w) {
      fireAtColumn = 0;
      fireAtRow++;
      if (fireAtRow >= h) {
        fireAtRow = 0;
      }
      coord = new Coordinate(fireAtRow, fireAtColumn);
    } else {
      coord = new Coordinate(fireAtRow, fireAtColumn);
      fireAtColumn++;
    }
    Ship<Character> ship = enemyBoard.fireAt(coord);
    computerReportHit(ship, coord);
  }

  protected void computerReportHit(Ship<Character> ship, Coordinate coord) {
    if (ship != null) {
      out.println("Player " + name + " hit your " + ship.getName() + " at " + coord.toString() + "!\n");
    }
  }
}
