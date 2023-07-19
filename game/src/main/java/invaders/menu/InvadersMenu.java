package invaders.menu;

import game.GameComponent;
import game.GameLogic;
import menu.AbstractMenu;
import menu.AbstractMenuPage;
import menu.MenuPage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class InvadersMenu extends AbstractMenu {

    public InvadersMenu(GameComponent gameComponent) {
        registerMenu(new StartMenuPage( gameComponent, this));
        registerMenu(new ConfigMenuPage( gameComponent,this));
        registerMenu(new SoundMenuPage( gameComponent, this));
        registerMenu(new GameOverMenuPage( gameComponent, this));
        registerMenu(new CreditsMenuPage( gameComponent, this));
        setMenuPage("KEY_START_MENU");
    }

    @Override
    public void init(GameComponent gameComponent) {
        setMenuPage(StartMenuPage.KEY);
    }
}
