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

    public static final String TIT_GAME_MENU = "GAME MENU";
    public static final String TIT_CONFIGURATIONS_MENU = "CONFIGURATIONS MENU";
    public static final String TIT_SOUND_MENU = "SOUND MENU";
    public static final String TIT_GAME_OVER = "GAME OVER";
    public static final String TIT_CREDITS = "CREDITS";

    public InvadersMenu(GameComponent gameComponent) {

        registerMenu(new StartMenuPage(TIT_GAME_MENU, gameComponent, this));
        registerMenu(new ConfigMenuPage(TIT_CONFIGURATIONS_MENU, gameComponent,this));
        registerMenu(new SoundMenuPage(TIT_SOUND_MENU, gameComponent, this));
        registerMenu(new GameOverMenuPage(TIT_GAME_OVER, gameComponent, this));
        registerMenu(new CreditsMenuPage(TIT_CREDITS, gameComponent, this));
        setMenuPage(StartMenuPage.KEY);
    }

}
