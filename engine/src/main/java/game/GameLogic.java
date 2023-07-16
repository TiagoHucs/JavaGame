package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface GameLogic {

    void init(GameComponent gameComponent);

    void draw(Graphics g);

    void update(GameComponent gameComponent, float delta);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);
}
