package edu.duke.ph139.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;
  final ArrayList<String> shipToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationsFns;

  /**
   * Constructs a TextPlayer for the battleship game.
   * 
   * @param theBoard    is the player's board.
   * @param input       is the user input reader.
   * @param output      is the game output for user.
   * @param shipFactory is the factory to create player's ships.
   * @param name        is the name of the player.
   */
  public TextPlayer(Board<Character> theBoard, BufferedReader input, PrintStream output,
      AbstractShipFactory<Character> shipFactory, String name) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = input;
    this.out = output;
    this.shipFactory = shipFactory;
    this.name = name;
    this.shipToPlace = new ArrayList<>();
    this.shipCreationsFns = new HashMap<>();
    setupShipCreationList();
    setupShipCreationMap();
  }

  /**
   * Sets up the ship creation map, which maps the name of the predifined ships to
   * the function that instantiates them.
   */
  protected void setupShipCreationMap() {
    shipCreationsFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationsFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationsFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationsFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  /**
   * Sets up the ship creation list to define the ships to create for a player.
   */
  protected void setupShipCreationList() {
    shipToPlace.addAll(Collections.nCopies(1, "Submarine"));
    shipToPlace.addAll(Collections.nCopies(1, "Destroyer"));
    shipToPlace.addAll(Collections.nCopies(1, "Battleship"));
    shipToPlace.addAll(Collections.nCopies(0, "Carrier"));
  }

  /**
   * Constructs a Placement object by reading a line from the input. Prompts the
   * player with the specified prompt.
   * 
   * @param prompt asks the player to enter a Ship placement.
   * @return the Placement of the Ship.
   * @throws IllegalArgumentException if the Placement fails.
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.print(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    return new Placement(s);
  }

  /**
   * Prompts the player do one Placement for the specified Ship name and Ship
   * creation function. Displays the player's board after the placement
   * 
   * @param shipName is the name of the Ship.
   * @param createFn is the Ship creation function.
   * @throws IllegalArgumentException if tryAddShip, read Placement, or Ship
   *                                  creation fails.
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    String prompt = "Player " + name + " where do you want to place a " + shipName + "?\n";
    Placement p = readPlacement(prompt);
    Ship<Character> s = createFn.apply(p);
    String addShipResult = theBoard.tryAddShip(s);
    if (addShipResult != null) {
      throw new IllegalArgumentException(addShipResult);
    }
    String output = view.displayMyOwnBoard();
    out.println(output);
  }

  /**
   * Prompts the player do the placement phase of the battleship game. Prompts the
   * player for each ship to place. If the placement is invalid, explain the
   * problem and asks the player to place again.
   */
  public void doPlacementPhase() throws IOException {
    out.println(view.displayMyOwnBoard());
    out.println("Player " + name
        + ": you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n");
    for (int i = 0; i < shipToPlace.size(); i++) {
      String shipName = shipToPlace.get(i);
      try {
        doOnePlacement(shipName, shipCreationsFns.get(shipName));
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage() + "\n");
        i--;
      }
    }
  }

  public Coordinate readCoordinate(String prompt) throws IOException {
    out.print(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    return new Coordinate(s);
  }


  protected void fireAction(Board<Character> enemyBoard) throws IOException {
    Coordinate c = readCoordinate("Please enter a coordinate to fire at!\n");
    String checkResult = enemyBoard.checkCoordinateInBounds(c);
    if (checkResult != null) {
      throw new IllegalArgumentException(checkResult);
    }
    Ship<Character> s = enemyBoard.fireAt(c);
    if (s != null) {
      out.println("You hit a " + s.getName() + "!\n");
    } else {
      out.println("You missed!\n");
    }
  }

  public void playOneTurn(Board<Character> enemyBoard) throws IOException {
    fireAction(enemyBoard);
  }

  public void doAttackingPhase(Board<Character> enemyBoard, BoardTextView enemyBoardView, String enemyHeader)
      throws IOException {
    out.print("Player " + name + "'s turn:\n");
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyBoardView, "Your ocean", enemyHeader));
    boolean validPlay = false;
    while (!validPlay) {
      try {
        playOneTurn(enemyBoard);
        validPlay = true;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage() + "\n");
        validPlay = false;
      }
    }
  }

}
