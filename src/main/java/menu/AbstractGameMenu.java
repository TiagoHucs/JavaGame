package menu;

import game.GameComponent;
import game.GameLogic;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGameMenu implements GameLogic {

    public static final String SND_TAB = "changing-tab.wav";
    public static final String SND_TIC = "tap.wav";

    protected final GameComponent gameComponent;

    private String[] options = new String[]{};
    private List<String> text;
    private int selectedOption = 0;
    private final Font font = ResourceManager.get().getFont();
    private FontMetrics metrics;
    private int line = 0;
    private final Image logo;

    public AbstractGameMenu(GameComponent gameComponent) {
        this.gameComponent = gameComponent;
        this.init(gameComponent);
        try {
            logo = ResourceManager.get().getImage("/image/logo.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(GameComponent gameComponent) {

    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        setStartLine();
        drawGameLogo(g);
        g.setFont(font);
        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }
        if(text != null){
            drawText(g,gameComponent);
        }
        drawOptions(g, gameComponent);
    }

    private void drawOptions(Graphics g, GameComponent gameComponent) {
        for (int i = 0; i < options.length; i++) {
            g.setColor(i == selectedOption ? Color.YELLOW : Color.WHITE);
            int larguraTexto = metrics.stringWidth(options[i]);
            int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
            g.drawString(options[i], x, line);
            nextLine(16);//TODO: pegar automaticamente o tamanho da fonte
        }
    }

    private void drawText(Graphics g, GameComponent gameComponent) {
        for (String textLine: text) {
            g.setColor(Color.WHITE);
            int larguraTexto = metrics.stringWidth(textLine);
            int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
            g.drawString(textLine, x, line);
            nextLine(16);//TODO: pegar automaticamente o tamanho da fonte
        }
    }

    private void drawGameLogo(Graphics g){
        g.drawImage(logo,
                gameComponent.getCfg().getLarguraTela()/2 - logo.getWidth(gameComponent) / 2,
                gameComponent.getCfg().getAlturaTela()/5,
                null,
                gameComponent);
        nextLine(logo.getHeight(gameComponent)/2);
    }

    private void nextLine(int heigh){
        line = line + heigh +20;
    }

    private void setStartLine(){
        line = gameComponent.getCfg().getAlturaTela()/3;
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

    public void setText(List<String> text) {
        this.text = text;
    }

}
