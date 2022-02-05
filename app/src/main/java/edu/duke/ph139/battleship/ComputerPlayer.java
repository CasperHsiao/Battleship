package edu.duke.ph139.battleship;

import java.io.PrintStream;
import java.util.ArrayList;

public class ComputerPlayer extends TextPlayer {
  final ArrayList<String> placementCommands;
  int fireAtRow;
  int fireAtColumn;

  /**
   * Constructs a TextPlayer for the battleship game.
   * 
   * @param theBoard    is the player's board.
   * @param output      is the game output for the other player..
   * @param shipFactory is the factory to create player's ships.
   * @param name        is the name of the player.
   */
  public ComputerPlayer(Board<Character> theBoard, PrintStream out, AbstractShipFactory<Character> shipFactory,
      String name) {
    super(theBoard, null, out, shipFactory, name);
    this.placementCommands = new ArrayList<>();
    this.fireAtRow = 0;
    this.fireAtColumn = 0;
    setupPlacementCommands();
  }

  /**
   * Sets up the placement commands for the computer player.
   */
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

  /**
   * Lets the computer place all its ships according to the placementCommands.
   */
  public void doPlacementPhase() {
    for (int i = 0; i < shipToPlace.size(); i++) {
      Placement p = new Placement(placementCommands.get(i));
      String shipName = shipToPlace.get(i);
      Ship<Character> s = shipCreationsFns.get(shipName).apply(p);
      theBoard.tryAddShip(s);
    }
  }

  /**
   * Lets the computer to attack the enemy's board in a top down fashion.
   * 
   * @param enemyBoard     is the board of the enemy player.
   * @param enemyBoardView is the board view of the enemy player.
   * @param enemyHeader    is the enemy board view's header.
   */
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

  /**
   * Lets the computer report the hit result to the enemy's output.
   * 
   * @param ship  is the ship that got hit.
   * @param coord is the coordinate of the fire.
   */
  protected void computerReportHit(Ship<Character> ship, Coordinate coord) {
    if (ship != null) {
      out.println("Player " + name + " hit your " + ship.getName() + " at " + coord.toString() + "!\n");
    }
  }
}
