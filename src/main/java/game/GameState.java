package game;

public class GameState {
    public enum State {
        INTRO,
        MENU,
        PLAY,
        GAMEOVER,
        QUIT
    }

    public State state = State.MENU;

    public boolean isGameRunning() {
        return !this.equals(State.QUIT);
    }
}
