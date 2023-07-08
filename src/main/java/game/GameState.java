package game;

public class GameState {
    public enum State {
        MENU,
        PLAY,
        QUIT
    }

    public State state = State.MENU;

    public boolean isGameRunning() {
        return !this.equals(State.QUIT);
    }
}
