package menu;

import game.GameComponent;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public abstract class AbstractGameMenu {

    public static final String SND_TAB = "changing-tab.wav";
    public static final String SND_TIC = "tap.wav";

    private final GameComponent gameComponent;

    private String[] options = new String[]{};
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

        g.drawImage(getImage("/image/hudmanu.JPG"),width,height,gameComponent);

/*        g.setColor(Color.BLACK);
        g.fillRect(width, height, width, height);

        g.setColor(Color.RED);
        g.drawRect(width, height, width, height);*/

        for (int i = 0; i < options.length; i++) {
            g.setColor(i == selectedOption ? Color.YELLOW : Color.WHITE);
            g.drawString(options[i], width + 60, height + 50 + ((i + 1) * 30));
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
            System.out.println("selecionou: " + options[selectedOption]);
            executeAction(options[selectedOption]);
        }

    }

    public abstract void executeAction(String action);

    public void setOptions(String[] options){
        this.options = options;
        this.selectedOption = 0;
    };

    public Image getImage(String filename) {
        try {
            return ResourceManager.get().getImage(filename, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
