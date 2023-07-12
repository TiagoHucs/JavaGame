package menu;

import game.GameComponent;
import game.GameLogic;
import menu.impl.GameOverMenuPage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameOverMenu implements GameLogic {

    private List<MenuPage> pages = new ArrayList<>(2);
    private MenuPage selectedPage;

    public GameOverMenu(GameComponent gameComponent) {
        pages.add(new GameOverMenuPage(gameComponent));
        selectedPage = pages.get(0);
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
}
