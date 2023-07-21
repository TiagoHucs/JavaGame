package pong;

import engine.GameWindow;

import java.awt.*;

public class Pong {
    public static void main(String[] args) {
        Dimension viewport = new Dimension(320, 180);
        GameWindow game = new GameWindow(viewport, new PongGameComponent());
        game.run();
    }
}
