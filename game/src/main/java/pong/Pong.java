package pong;

import engine.GameWindow;

public class Pong {
    public static void main(String[] args) {
        new GameWindow(new PongGameComponent()).play();
    }
}
