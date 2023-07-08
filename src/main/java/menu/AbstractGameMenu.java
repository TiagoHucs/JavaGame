package menu;

import game.GameComponent;
import game.GameLogic;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public abstract class AbstractGameMenu implements GameLogic {

    public static final String SND_TAB = "changing-tab.wav";
    public static final String SND_TIC = "tap.wav";

    private final GameComponent gameComponent;

    private String[] options = new String[]{};
    protected int selectedOption = 0;
    private final int width;
    private final int height;
    private Font font;
    private FontMetrics metrics;

    public AbstractGameMenu(GameComponent gameComponent) {
        this.gameComponent = gameComponent;
        this.width = gameComponent.getCfg().getLarguraTela() / 3;
        this.height = gameComponent.getCfg().getAlturaTela() / 3;
        this.init();
    }

    @Override
    public void init() {
        this.font = new Font("Arial", Font.PLAIN, 16);
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {

        Color myColour = new Color(10, 10, 10, 200);
        g.setColor(myColour);
        g.fillRect(width,height,width,height);
        g.setColor(Color.RED);
        g.drawRect(width,height,width,height);

        g.setFont(font);

        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        for (int i = 0; i < options.length; i++) {
            g.setColor(i == selectedOption ? Color.YELLOW : Color.WHITE);
            int larguraTexto = metrics.stringWidth(options[i]);
            int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
            g.drawString(options[i], x, height + 30 + ((i + 1) * 30));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

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

    public void setOptions(String[] options) {
        this.options = options;
        this.selectedOption = 0;
    }

    public Image getImage(String filename) {
        try {
            return ResourceManager.get().getImage(filename, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
