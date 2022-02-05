package edu.duke.ph139.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;

public class V2TextPlayer extends TextPlayer {
  final MoveShip<Character> moveAction;
  final SonarScan<Character> sonarAction;

  public V2TextPlayer(Board<Character> theBoard, BufferedReader input, PrintStream output,
      AbstractShipFactory<Character> shipFactory, String name) {
    super(theBoard, input, output, shipFactory, name);
    this.moveAction = new MoveShip<>(3);
    this.sonarAction = new SonarScan<>(3);
  }  

  protected char readAction(String prompt) throws IOException {
    out.print(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    s = s.toUpperCase();
    if (!s.equals("F") && !s.equals("M") && !s.equals("S")) {
      throw new IllegalArgumentException("The action must be (F)ire, (M)ove, or (S)onar but is " + s);
    }
    return s.charAt(0);
  }
  
  public void moveShip() throws IOException {
    Coordinate c = readCoordinate("Please select one of your ships to move.\n");
    Ship<Character> toMove = theBoard.selectShip(c);
    if (toMove == null) {
      throw new IllegalArgumentException("No ship occupies the given coordinate!");
    }
    Placement newPlacement = readPlacement("Where do you want to place the " + toMove.getName() + "?\n");
    Ship<Character> newShip = shipCreationsFns.get(toMove.getName()).apply(newPlacement);
    moveAction.useAction(theBoard, toMove, newShip);
  }

  public void sonarScan(Board<Character> enemyBoard) throws IOException {
    Coordinate c = readCoordinate("Please enter a center coordinate for the sonar.\n");
    String checkResult = enemyBoard.checkCoordinateInBounds(c);
    if (checkResult != null) {
      throw new IllegalArgumentException(checkResult);
    }
    String result = sonarAction.useAction(enemyBoard, c);
    out.println(result);
  }

  @Override
  public void playOneTurn(Board<Character> enemyBoard) throws IOException {
    String actionPrompt = "Possible actions for Player " + this.name
        + ":\n\n F Fire at a square\n M Move a ship to another square (" + moveAction.getMovesLeft()
        + " remaining)\n S Sonar scan (" + sonarAction.getMovesLeft() + " remaining)\n\nPlayer " + this.name
        + ", what would you like to do?\n";
    char action = readAction(actionPrompt);
    if (action == 'F') {
      fireAction(enemyBoard);
    }
    if (action == 'M') {
      if (moveAction.getMovesLeft() <= 0) {
        throw new IllegalArgumentException("You have no move ship action left!");
      }
      moveShip();
    }
    if (action == 'S') {
      if (sonarAction.getMovesLeft() <= 0) {
        throw new IllegalArgumentException("You have no sonar scan action left!");
      }
      sonarScan(enemyBoard);
    }
  }

}
