package game;

public class GameState {
    public enum State {
        MENU,
        PLAY,
        GAMEOVER,
        QUIT
    }

    public State state = State.MENU;

    public boolean isGameRunning() {
        return !this.equals(State.QUIT);
    }

    public void toggle(State a, State b) {
        this.state = this.state == a ? b : a;
    }
}
