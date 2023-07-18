package pong;

import engine.GameWindow;

import java.awt.*;

public class Pong {
    public static void main(String[] args) {
        Dimension viewport = new Dimension(1280, 720);
        new GameWindow(viewport, new PongGameComponent()).play();
    }
}
