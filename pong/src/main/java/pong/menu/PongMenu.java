package pong.menu;

import game.GameComponent;
import menu.AbstractMenu;

public class PongMenu extends AbstractMenu {

    public PongMenu(GameComponent gameComponent) {
        registerMenu(new PongMenuPage(gameComponent));
        setMenuPage(PongMenuPage.KEY);
    }

    @Override
    public void init(GameComponent gameComponent) {

    }

}
