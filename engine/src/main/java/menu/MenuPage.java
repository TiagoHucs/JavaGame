package menu;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface MenuPage {

    void draw(Graphics g);

    void sendEvent(KeyEvent e);

}
