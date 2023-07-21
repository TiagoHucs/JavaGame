package rpg;

import engine.GameWindow;

import java.awt.*;

public class Rpg {
    public static void main(String[] args) {
        Dimension viewport = new Dimension(32 * 16, 20 * 16);
        GameWindow game = new GameWindow(viewport, new RpgGameComponent());
        game.run();
    }
}
