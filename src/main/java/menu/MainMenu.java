package menu;

import game.GameComponent;
import game.GameLogic;
import menu.impl.ConfigMenuPage;
import menu.impl.SoundMenuPage;
import menu.impl.StartMenuPage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class MainMenu implements GameLogic {

    private HashMap<String,MenuPage> pages = new HashMap<>(2);
    private MenuPage selectedPage;

    public MainMenu(GameComponent gameComponent) {

        pages.put("GAME MENU",new StartMenuPage("GAME MENU",gameComponent,this));
        pages.put("CONFIGURATIONS MENU",new ConfigMenuPage("CONFIGURATIONS MENU",gameComponent,this));
        pages.put("SOUND MENU",new SoundMenuPage("SOUND MENU",gameComponent,this));

        setMenuPage("GAME MENU");
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
