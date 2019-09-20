package ui;

import game.Game;
import game.Game.Ship;
import game.GridSquare;
import org.jetbrains.annotations.Nullable;
import ui.panels.*;
import ui.panels.GridPanel.Special;
import ui.panels.ShipPickPanel.PickerPhase;
import ui.panels.ShipPlacementPanel.PlacementPhase;

import java.util.ArrayList;
import java.util.Scanner;

import static ui.CLIStrings.*;


public class CLI {

    private Game game;
    private Scanner in;
    private ArrayList<Ship> playerShips;
    private final int TOTAL_SHIPS = 5;
    private Boolean successfulHit;
    private boolean placementClearRequested;

    public CLI(Game game) {

        this.game = game;
        this.in = new Scanner(System.in);
        this.playerShips = new ArrayList<>();
        this.placementClearRequested = false;
        initShips();
    }

    public void run() {
        resetScreen();
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

        // Sanity check in case of future development.
        if (playerShips.size() != TOTAL_SHIPS) {
            throw new IllegalStateException("TOTAL_SHIPS not equal to number of ships added to fleet.");
        }
    }


    private void placeShips() {

        ShipPickPanel pickPanel = new ShipPickPanel();
        ShipPlacementPanel placePanel = new ShipPlacementPanel();

        while (true) {

            // Handle the final ship placement.
            if (playerShips.size() == 1) {
                Ship ship = playerShips.get(0);
                placePanel.configure(game.playerGridToString(),
                        ship, PlacementPhase.LAST);
                placeShip(ship, placePanel);
                break;
            }

            // Handle all non-final placements.
            StringBuilder shipPicker = new StringBuilder();
            for (Ship ship : playerShips) {
                shipPicker.append(String.format("   [%d] %s (Size %d)\n",
                        playerShips.indexOf(ship) + 1, ship.name(), ship.getSize()));
            }

            if (playerShips.size() == TOTAL_SHIPS){
                pickPanel.configure(game.playerGridToString(),
                        shipPicker.toString(), PickerPhase.FIRST, false);
            } else {
                pickPanel.configure(game.playerGridToString(),
                        shipPicker.toString(), PickerPhase.INTERMEDIATE, false);
            }

            boolean valid = false;

            while (! valid) {

                String input = interact(pickPanel);

                try {
                    int optionInput = Integer.parseInt(input);

                    if (optionInput >= 1 && optionInput <= playerShips.size()) {

                        Ship ship = playerShips.get(optionInput-1);
                        if (playerShips.size() == TOTAL_SHIPS) {
                            placePanel.configure(game.playerGridToString(), ship,
                                    PlacementPhase.FIRST);
                        } else {
                            placePanel.configure(game.playerGridToString(), ship,
                                    PlacementPhase.INTERMEDIATE);
                        }
                        placeShip(ship, placePanel);
                        playerShips.remove(ship);
                        valid = true;
                    }

                } catch (NumberFormatException e) {
                    if (input.equalsIgnoreCase("C")) {
                        this.placementClearRequested = true;
                        valid = true;
                    }
                    else if (input.equalsIgnoreCase("Q")) {
                        quit();
                        valid = true;
                    }
                }

                if (placementClearRequested) {
                    initShips();
                    game.clearPlayerShips();
                    pickPanel.setSpecial(SPECIAL_PLACEMENTS_CLEARED, Special.TOP);
                    this.placementClearRequested = false;
                }
                pickPanel.setError(!valid);
            }
        }
    }


    private void placeShip(Ship ship, ShipPlacementPanel panel) {

        boolean finished = false;
        boolean facingDown = true;

        while (! finished) {

            String input = interact(panel);

            // If the user wishes to toggle the ship direction:
            if (input.equalsIgnoreCase("T")) {
                facingDown = !(facingDown);
                panel.toggleShipDirection();
            }
            // If the user enters an existing coordinate:
            else if (GridSquare.isValidCoordinate(input)) {

                boolean validCo = game.placeShip(ship, input, facingDown);
                panel.setError(!(validCo), ERR_PLACEMENT);
                finished = validCo;
            }
            // If the user wishes to clear their ship placements:
            else if (input.equalsIgnoreCase("C")) {
                this.placementClearRequested = true;
                finished = true;
            }
            // If the user wishes to quit:
            else if (input.equalsIgnoreCase("Q")) {
                quit();
            }
            else {
                panel.setError(true);
            }
        }
    }


    private void establishTurn() {

        // TODO: Look into turning this into a full grid panel to confirm ship placements.
        TurnEstablishPanel panel = new TurnEstablishPanel();
        boolean finished = false;

        while (! finished) {
            String input = interact(panel);

            if (input.equals("1")) {
                game.setPlayersTurn(true);
                finished = true;
            }
            else if (input.equals("2")) {
                game.setPlayersTurn(false);
                finished = true;
            }
            else if (input.equalsIgnoreCase("Q")) {
                quit();
            }
            else {
                panel.setError(true);
            }
        }
    }

    private void runGame() {

        boolean gameWon = false;

        while (!gameWon) {

            if (game.isPlayersTurn()) {
                playerTurn();
            } else {
                opponentTurn();
            }
            gameWon = game.turn();
        }
    }


    private void playerTurn(){

        PlayerTurnPanel panel = new PlayerTurnPanel(successfulHit, game.toString());

        while(true) {

            String input = interact(panel);

            if (GridSquare.isValidCoordinate(input)) {
                GridSquare target = new GridSquare(input);
                panel.triggerExtension(input);
                this.successfulHit = confirmHit(panel);
                game.registerPlayerStrike(target, successfulHit);
                return;
            }
            else if (input.equalsIgnoreCase("Q")) {
                quit();
            }
            else {
                panel.setError(true);
            }
        }
    }


    private boolean confirmHit(PlayerTurnPanel panel) {

        while (true) {

            String input = interact(panel);

            if (input.equalsIgnoreCase("Y")) {
                return true;
            }
            else if (input.equalsIgnoreCase("N")) {
                return false;
            }
            else if (input.equalsIgnoreCase("Q")) {
                quit();
            } else {
                panel.setError(true, ERR_DEFAULT);
            }
        }
    }


    private void opponentTurn() {

        // TODO: Investigate missing request.
        OpponentTurnPanel panel = new OpponentTurnPanel(successfulHit, game.toString());

        while(true) {

            String input = interact(panel);

            if (GridSquare.isValidCoordinate(input)) {

                GridSquare target = new GridSquare(input);
                this.successfulHit = game.registerOpponentStrike(target);
                return;
            }
            else if (input.equalsIgnoreCase("Q")) {
                quit();
            }
            else {
                panel.setError(true);
            }
        }
    }


    private void endGame() {

        StringBuilder message = new StringBuilder();

        resetScreen();
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
        in.nextLine();
    }


    private void quit() {

        resetScreen();
        QuitPanel panel = new QuitPanel();
        System.out.print(panel.toString());

        if (in.nextLine().equalsIgnoreCase("Y")) {
            System.out.print("\n\n"+ SIMPLE_BYE + "\n");
            System.exit(0);
        }
    }


    private String interact(CLIPanel panel) {
        resetScreen();
        System.out.print(panel);
        return in.nextLine();
    }

    private void resetScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
