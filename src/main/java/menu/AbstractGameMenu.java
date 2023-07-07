package menu;

import game.GameComponent;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class AbstractGameMenu {

    public static final String SND_TAB = "changing-tab.wav";
    public static final String SND_TIC = "tap.wav";

    private final GameComponent gameComponent;

    protected String[] options = new String[]{};
    protected int selectedOption = 0;
    private final int width;
    private final int height;

    public AbstractGameMenu(GameComponent gameComponent) {
        this.gameComponent = gameComponent;
        this.width = gameComponent.getCfg().getLarguraTela() / 3;
        this.height = gameComponent.getCfg().getAlturaTela() / 3;
    }

    public void paintMenu(Graphics g) {

        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        g.setColor(Color.BLACK);
        g.fillRect(width, height, width, height);

        g.setColor(Color.RED);
        g.drawRect(width, height, width, height);

        for (int i = 0; i < options.length; i++) {
            g.setColor(i == selectedOption ? Color.YELLOW : Color.WHITE);
            g.drawString(options[i], width + 10, height + ((i + 1) * 30));
        }
    }

    public void control(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (selectedOption > 0) {
                selectedOption--;
                gameComponent.getSoundManager().playSound(SND_TIC);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (selectedOption < options.length - 1) {
                selectedOption++;
                gameComponent.getSoundManager().playSound(SND_TIC);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameComponent.getSoundManager().playSound(SND_TAB);
            executeAction(options[selectedOption]);
        }

    }

    public abstract void executeAction(String action);
}
