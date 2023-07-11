package menu;

import game.GameComponent;
import game.GameState;

import java.awt.event.KeyEvent;

public class IntroMenu extends AbstractGameMenu {

    public static final String OPT_RETOMAR_PARTIDA = "PRESS ANY KEY";

    private final String[] defaultOptions = {OPT_RETOMAR_PARTIDA};

    private GameComponent gameComponent;

    public IntroMenu(GameComponent gameComponent) {
        super(gameComponent);
        this.gameComponent = gameComponent;
        this.setOptions(defaultOptions);
    }

    @Override
    public void executeAction(String action) {
        gameComponent.gameState.state = GameState.State.MENU;
    }

    @Override
    public void update(GameComponent gameComponent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
