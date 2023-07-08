package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface GameLogic {

    void init();

    void draw(Graphics g, GameComponent gameComponent);

    void update(GameComponent gameComponent);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);
}
