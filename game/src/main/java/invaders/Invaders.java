package invaders;

import engine.GameWindow;

public class Invaders {
    public static void main(String[] args) {
        new GameWindow(new InvadersGameComponent()).play();
    }
}
