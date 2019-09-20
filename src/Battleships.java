import game.Game;
import ui.CLI;
import java.io.IOException;

public class Battleships {

    public static void main(String[] args) {

        Game game = new Game();

        CLI ui = new CLI(game);
        ui.run();
    }
}
