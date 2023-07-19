package pong.menu;

import game.GameComponent;
import game.GameState;
import menu.AbstractMenuOption;
import menu.AbstractMenuPage;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class PongMenuPage extends AbstractMenuPage {

    public static final String KEY = "KEY_PONG_MENU_PAGE";

    public PongMenuPage(GameComponent gameComponent) {
        super(gameComponent);

        setOptions(Arrays.asList(
                new AbstractMenuOption("Back Game") {
                    @Override
                    public void execute() {
                        gameComponent.gameState.state = GameState.State.PLAY;
                    }
                },
                new AbstractMenuOption("Exit Game") {
                    @Override
                    public void execute() {
                        gameComponent.gameState.state = GameState.State.QUIT;
                    }
                }
        ));
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public void sendEvent(KeyEvent e) {
        super.sendEvent(e);
    }
}
