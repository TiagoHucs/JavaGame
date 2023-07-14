package menu;

import game.GameComponent;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface MenuPage {

    public void draw(Graphics g, GameComponent gameComponent);

    void sendEvent(KeyEvent e);
}
