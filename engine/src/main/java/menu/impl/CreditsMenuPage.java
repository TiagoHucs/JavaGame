package menu.impl;

import game.GameComponent;
import menu.AbstractMenuPage;
import menu.MainMenu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class CreditsMenuPage extends AbstractMenuPage {

    public static final String OPT_BACK_GAME = "CONTINUE/RETURN GAME";
    private static final String OPT_CONFIGURATIONS = "CONFIGURATIONS";
    private static final String OPT_EXIT_GAME = "EXIT TO DESKTOP";

    public CreditsMenuPage(String title, GameComponent gameComponent, MainMenu mainMenu) {
        super(title, gameComponent);
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
