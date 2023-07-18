package rpg;

import engine.GameWindow;

public class Rpg {
    public static void main(String[] args) {
        new GameWindow(new RpgGameComponent()).play();
    }
}
