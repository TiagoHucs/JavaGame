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

    private HashMap<String, MenuPage> pages = new HashMap<>(4);
    private MenuPage selectedPage;

    public static final String KEY_GAME_MENU = "GAME MENU";
    public static final String KEY_CONFIGURATIONS_MENU = "CONFIGURATIONS MENU";
    public static final String KEY_SOUND_MENU = "SOUND MENU";
    public static final String KEY_GAME_OVER = "GAME OVER";

    public MainMenu(GameComponent gameComponent) {

        registerMenu(new StartMenuPage(KEY_GAME_MENU, gameComponent, this));
        registerMenu(new ConfigMenuPage(KEY_CONFIGURATIONS_MENU, gameComponent,this));
        registerMenu(new SoundMenuPage(KEY_SOUND_MENU, gameComponent, this));
        registerMenu(new GameOverMenuPage(KEY_GAME_OVER, gameComponent, this));

        setMenuPage(KEY_GAME_MENU);
    }

    private void registerMenu(AbstractMenuPage menu) {
        pages.put(menu.getTitle(), menu);
    }

    @Override
    public void init(GameComponent gameComponent) {
        setMenuPage(KEY_GAME_MENU);
    }

    @Override
    public void draw(Graphics g) {
        selectedPage.draw(g);
    }

    @Override
    public void update(GameComponent gameComponent, float delta) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        selectedPage.sendEvent(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setMenuPage(String menuPageKey) {
        selectedPage = pages.get(menuPageKey);
    }
}
