import Model.Game;
import View.*;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws AWTException {
        Game game = new Game();
        View view = new View(game);
    }
}
