package edu.duke.ph139.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class TextPlayer {
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final String name;

  public TextPlayer(Board<Character> theBoard, BufferedReader input, PrintStream output,
      AbstractShipFactory<Character> shipFactory, String name) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = input;
    this.out = output;
    this.shipFactory = shipFactory;
    this.name = name;
  }

  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  public void doOnePlacement() throws IOException {
    String prompt = "Player " + name + " where do you want to place a destroyer?";
    Placement p = readPlacement(prompt);
    Ship<Character> s = shipFactory.makeDestroyer(p);
    theBoard.tryAddShip(s);
    String output = view.displayMyOwnBoard();
    out.print(output);
  }

  public void doPlacementPhase() throws IOException{
    out.print(view.displayMyOwnBoard());
    out.print("Player A: you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2 \n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n");
    doOnePlacement();
  }

}
