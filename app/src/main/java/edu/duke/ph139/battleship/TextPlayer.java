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

  protected void setupShipCreationMap() {
    shipCreationsFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationsFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationsFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationsFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }

  protected void setupShipCreationList() {
    shipToPlace.addAll(Collections.nCopies(1, "Submarine"));
    shipToPlace.addAll(Collections.nCopies(1, "Destroyer"));
    shipToPlace.addAll(Collections.nCopies(1, "Battleship"));
    shipToPlace.addAll(Collections.nCopies(0, "Carrier"));
  }

  public Placement readPlacement(String prompt) throws IOException {
    out.print(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    return new Placement(s);
  }

  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    String prompt = "Player " + name + " where do you want to place a " + shipName + "?\n";
    Placement p = readPlacement(prompt);
    Ship<Character> s = createFn.apply(p);
    String addShipResult = theBoard.tryAddShip(s);
    if (addShipResult != null) {
      throw new IllegalArgumentException(addShipResult);
    }
    String output = view.displayMyOwnBoard();
    out.print(output);
  }

  public void doPlacementPhase() throws IOException {
    out.print(view.displayMyOwnBoard());
    out.print("Player " + name
        + ": you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n");

    for (int i = 0; i < shipToPlace.size(); i++) {
      String shipName = shipToPlace.get(i);
      try {
        doOnePlacement(shipName, shipCreationsFns.get(shipName));
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
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

  public void checkCoordinate(Coordinate c, Board<Character> board) {
    if (c.getRow() < 0 || c.getRow() >= board.getHeight()) {
      throw new IllegalArgumentException("The coordinate entered is out of bounds.");
    }
    if (c.getColumn() < 0 || c.getColumn() >= board.getWidth()) {
      throw new IllegalArgumentException("The coordinate entered is out of bounds.");
    }
  }

  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyBoardView, String enemyHeader)
      throws IOException {
    out.println("Player " + name + "'s turn:");
    out.print(view.displayMyBoardWithEnemyNextToIt(enemyBoardView, "Your ocean", enemyHeader));
    boolean validPlay = false;
    while (!validPlay) {
      try {
        Coordinate c = readCoordinate("Please enter a coordinate to fire at!\n");
        checkCoordinate(c, enemyBoard);
        Ship<Character> s = enemyBoard.fireAt(c);
        if (s != null) {
          out.println("You hit a " + s.getName() + "!");
        } else {
          out.println("You missed!");
        }
        validPlay = true;
      } catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        validPlay = false;
      }
    }
  }
}
