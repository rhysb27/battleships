package ui;

import game.Game;
import game.Game.Ship;
import game.GridSquare;

import java.util.ArrayList;
import java.util.Scanner;


public class CLI {

    private Game game;
    private Scanner in;
    private ArrayList<Ship> playerShips;

    public CLI(Game game) {

        this.game = game;
        this.in = new Scanner(System.in);
        this.playerShips = new ArrayList<>();
        initShips();
    }

    public void run() {

        System.out.print( "\n----------- Welcome to Battleships! -----------");
        placeShips();
        establishTurn();
        runGame();
        endGame();
    }


    private void initShips() {

        playerShips.clear();
        playerShips.add(Ship.Gunship);
        playerShips.add(Ship.Destroyer);
        playerShips.add(Ship.Submarine);
        playerShips.add(Ship.Battleship);
        playerShips.add(Ship.Carrier);

    }


    private void placeShips() {

        System.out.print("\n\n It's time to place your ships!");

        while (true) {

            if (playerShips.size() == 1) {
                System.out.print("\n" + game.playerGridToString()
                        + "\n\nLast one!");
                placeShip(playerShips.get(0));
                System.out.print("\nAll ships entered! Thank you.");
                break;
            }

            System.out.print("\n" + game.playerGridToString()
                    + "\n\nPlease select one of the following ships to place. "
                    + "\nPress [C] to clear and start again, or [Q] to quit.\n");

            for (Ship ship : playerShips) {
                String out = String.format("\n   [%d] %s (Size %d)",
                        playerShips.indexOf(ship) + 1, ship.name(), ship.getSize());
                System.out.print(out);
            }

            boolean valid = false;

            while (! valid) {

                System.out.print("\n\n  > ");
                String input = in.nextLine();

                try {
                    int optionInput = Integer.parseInt(input);

                    if (optionInput >= 1 && optionInput <= playerShips.size()) {
                        placeShip(playerShips.get(optionInput - 1));
                        playerShips.remove(optionInput - 1);
                        valid = true;

                    } else {
                        System.out.print("\nWhoops! That wasn't an option.\n");
                    }

                } catch (NumberFormatException e) {
                    if (input.equalsIgnoreCase("C")) {
                        initShips();
                        game.clearPlayerShips();
                        System.out.print("\nBattleship placements cleared!");
                        valid = true;
                    }
                    else if (input.equalsIgnoreCase("Q")) {
                        quit("\nPlease continue adding your ships.");
                        valid = true;
                    }
                    else {
                        System.out.print("\nWhoops! That wasn't an option.\n");
                    }
                }
            }
        }
    }


    private void placeShip(Ship ship) {

        String prompt = String.format(
                "\nPlace your %s (size %d) by toggling direction with [T] and then entering the desired coordinates."
                + "\nYour %s is facing ",
                ship.name(), ship.getSize(), ship.name()
        );

        System.out.print(prompt + " DOWN.");

        boolean finished = false;
        boolean facingDown = true;

        while (! finished) {

            System.out.print("\n\n  > ");
            String input = in.nextLine();

            // If the user wishes to toggle the ship direction:
            if (input.equalsIgnoreCase("T")) {
                if (facingDown) {
                    facingDown = false;
                    System.out.print(String.format("\nYour %s is now facing ACROSS.", ship.name()));
                } else {
                    facingDown = true;
                    System.out.print(String.format("\nYour %s is now facing DOWN.", ship.name()));
                }
            }
            // If the user enters an existing coordinate:
            else if (GridSquare.isValidCoordinate(input)) {

                boolean validCo = game.placeShip(ship, input, facingDown);

                if (validCo) {
                    finished = true;
                } else {
                    System.out.print("\n" + game.playerGridToString());
                    System.out.print("\nWhoops! Looks like that ship doesn't fit there! Please try somewhere else.");
                }

            }
            // If the user wishes to clear their ship placements:
            else if (input.equalsIgnoreCase("C")) {
                initShips();
                game.clearPlayerShips();
                System.out.print("\nBattleship placements cleared!");
                finished = true;
            }
            // If the user wishes to quit:
            else if (input.equalsIgnoreCase("Q")) {
                String reprompt = game.playerGridToString() + "\n" + prompt;
                if (facingDown) {
                    reprompt += " DOWN.";
                } else {
                    reprompt += " ACROSS.";
                }
                quit(reprompt);
            }
            else {
                System.out.print("\nWhoops! That wasn't an option.\n");
            }
        }
    }


    private void establishTurn() {

        String prompt = "\n\nWhose turn is it first?"
                + "\n  [1] Mine"
                + "\n  [2] Theirs";

        System.out.print(prompt);

        boolean finished = false;

        while (! finished) {

            System.out.print("\n\n  > ");
            String input = in.nextLine();

            if (input.equals("1")) {
                game.setPlayersTurn(true);
                finished = true;
            }
            else if (input.equals("2")) {
                game.setPlayersTurn(false);
                finished = true;
            }
            else if (input.equalsIgnoreCase("Q")) {
                quit(prompt);
            }
            else {
                System.out.print("\nWhoops! That wasn't an option.");
            }
        }
    }


    private void runGame() {

        boolean gameWon = false;

        while (! gameWon) {

            if (game.isPlayersTurn()) {
                playerTurn();
            } else {
                opponentTurn();
            }

            gameWon = game.turn();
        }
    }


    private void playerTurn(){

        String prompt = "\n\n----- Your turn! ----- "
                + "\n" + game.opponentGridToString()
                + "\n\nPlease select a coordinate to fire upon!";

        System.out.print(prompt);

        boolean finished = false;

        while(! finished) {

            System.out.print("\n  > ");
            String input = in.nextLine();

            if (GridSquare.isValidCoordinate(input)) {
                GridSquare target = new GridSquare(input);
                boolean hit = confirmHit();

                game.registerPlayerStrike(target, hit);

                if (hit) {
                    System.out.print("\nNice!");
                } else {
                    System.out.print("\nUnlucky.");
                }

                finished = true;
            }
            else if (input.equalsIgnoreCase("Q")) {
                quit(prompt);
            }
            else {
                System.out.print("\nWhoops! That wasn't a valid coordinate/option.");
            }
        }
    }


    private boolean confirmHit() {

        String prompt = "\nHit? [Y] / [N]";
        System.out.print(prompt);

        boolean finished = false;
        boolean hit = false;

        while (! finished) {

            System.out.print("\n\n  > ");
            String input = in.nextLine();

            if (input.equalsIgnoreCase("Y")) {
                hit = true;
                finished = true;
            }
            else if (input.equalsIgnoreCase("N")) {
                finished = true;
            }
            else if (input.equalsIgnoreCase("Q")) {
                quit(prompt);
            } else {
                System.out.print("Whoops! That wasn't a valid coordinate/option.");
            }
        }

        return hit;
    }


    private void opponentTurn() {

        String prompt = "\n\n----- Opponent's turn! ----- "
                + "\n" + game.playerGridToString()
                + "\n\nPlease select the co-ordinate your opponent fired upon.";

        System.out.print(prompt);

        boolean finished = false;

        while(! finished) {
            System.out.print("\n  > ");
            String input = in.nextLine();

            if (GridSquare.isValidCoordinate(input)) {

                GridSquare target = new GridSquare(input);

                boolean hit = game.registerOpponentStrike(target);

                if (hit) {
                    System.out.print("\nOuch!");
                } else {
                    System.out.print("\nPhew.");
                }

                finished = true;
            }
            else if (input.equalsIgnoreCase("Q")) {
                quit(prompt);
            }
            else {
                System.out.print("\nWhoops! That wasn't a valid coordinate/option.");
            }
        }
    }


    private void endGame() {

        StringBuilder message = new StringBuilder();

        message.append("\n\n----- Game Over! -----\n\n");
        message.append(game.toString());

        Game.Winner winner = game.getWinner();

        if (winner == Game.Winner.Player_Win) {
            message.append("\n\nCongratulations! You won.");
        } else {
            message.append("\n\nCommiserations - you lost.");
        }

         message.append("\nHit [enter] to finish!");

        System.out.print(message.toString());
    }


    private void quit(String reprompt) {

        System.out.print("\nAre you sure you wish to quit? ([Y]/<any key>)");
        System.out.print("\n  > ");
        if (in.nextLine().equalsIgnoreCase("Y")) {
            System.out.print("\n\n------ Bye for now! ------\n");
            System.exit(0);
        } else {
            System.out.print(reprompt);
        }
    }

}
