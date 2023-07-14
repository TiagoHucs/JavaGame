package menu;

import game.GameComponent;
import game.GameLogic;
import menu.impl.ConfigMenuPage;
import menu.impl.GameOverMenuPage;
import menu.impl.SoundMenuPage;
import menu.impl.StartMenuPage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class MainMenu implements GameLogic {

    private HashMap<String,MenuPage> pages = new HashMap<>(4);
    private MenuPage selectedPage;

    public static final String KEY_GAME_MENU = "GAME MENU";
    public static final String KEY_CONFIGURATIONS_MENU = "CONFIGURATIONS MENU";
    public static final String KEY_SOUND_MENU = "SOUND MENU";
    public static final String KEY_GAME_OVER = "GAME OVER novo";

    public MainMenu(GameComponent gameComponent) {

        pages.put(KEY_GAME_MENU,new StartMenuPage(KEY_GAME_MENU,gameComponent,this));
        pages.put(KEY_CONFIGURATIONS_MENU,new ConfigMenuPage(KEY_CONFIGURATIONS_MENU,gameComponent,this));
        pages.put(KEY_SOUND_MENU,new SoundMenuPage(KEY_SOUND_MENU,gameComponent,this));

        pages.put(KEY_GAME_OVER,new GameOverMenuPage(KEY_SOUND_MENU,gameComponent,this));

        setMenuPage(KEY_GAME_MENU);
    }

    @Override
    public void init(GameComponent gameComponent) {

    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        selectedPage.draw(g,gameComponent);
    }

    @Override
    public void update(GameComponent gameComponent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        selectedPage.sendEvent(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setMenuPage(String menuPageKey){
        selectedPage = pages.get(menuPageKey);
    }
}
