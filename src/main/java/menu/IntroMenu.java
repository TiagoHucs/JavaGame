package menu;

import game.GameComponent;
import game.GameState;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class IntroMenu extends AbstractGameMenu {

    public IntroMenu(GameComponent gameComponent) {
        super(gameComponent);
    }

    @Override
    public void init(GameComponent gameComponent) {
        this.setText(Arrays.asList("A crazy space adventure ever!"));
        this.setOptions(new String[]{"PRESS ANY KEY"});
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
