package invaders.menu;

import game.GameComponent;
import menu.AbstractMenu;
import menu.AbstractMenuPage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class CreditsMenuPage extends AbstractMenuPage {

    public static final String KEY = "KEY_CREDITS";

    public CreditsMenuPage(GameComponent gameComponent, AbstractMenu menu) {
        super( gameComponent);
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public void draw(Graphics g) {
        g.setFont(font);

        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        setStartLine(gameComponent);
       // super.draw(g);
        List<String> nameList = Arrays.asList(
        "Chaya Glover",
        "Tomasz Pham",
        "Faris Riley",
        "Chaim Paul",
        "Kyla Hood",
        "Joyce Gaines",
        "Jed Thornton",
        "Zackary Kidd",
        "Delilah Kane",
        "Callan Leblanc");
        drawText(g,gameComponent,nameList);
    }

    @Override
    public void sendEvent(KeyEvent e) {
        super.sendEvent(e);
    }
}
