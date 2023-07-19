package menu;

import game.GameComponent;
import game.GameLogic;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public abstract class AbstractMenu implements GameLogic {

    protected HashMap<String, MenuPage> pages = new HashMap<>(4);
    protected MenuPage selectedPage;

    protected void registerMenu(AbstractMenuPage menu) {
        pages.put(menu.getTitle(), menu);
    }

    @Override
    public void init(GameComponent gameComponent) {

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
