package menu;

import game.GameComponent;
import game.GameLogic;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
public abstract class AbstractGameMenu implements GameLogic {

    public static final String SND_TAB = "changing-tab.wav";
    public static final String SND_TIC = "tap.wav";

    protected final GameComponent gameComponent;

    private String[] options = new String[]{};
    protected int selectedOption = 0;
    private final int width;
    private final int height;
    private final Font font = ResourceManager.get().getFont();
    protected FontMetrics metrics;
    protected int line = 0;

    public AbstractGameMenu(GameComponent gameComponent) {
        this.gameComponent = gameComponent;
        this.width = gameComponent.getCfg().getLarguraTela() / 3;
        this.height = gameComponent.getCfg().getAlturaTela() / 3;
        this.init(gameComponent);
    }

    @Override
    public void init(GameComponent gameComponent) {

    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {

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

    protected void writeText(Graphics g, Color color,String text, int size){
        g.setFont(font);
        metrics = g.getFontMetrics(font);
        int larguraTexto = metrics.stringWidth(text);
        int colunaInicial = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
        g.setColor(color);
        g.drawString(text, colunaInicial, line);
        nextLine(size);
    }

    protected void drawImage(Graphics g,Image image){
        g.drawImage(image,
                gameComponent.getCfg().getLarguraTela()/2 - image.getWidth(gameComponent) / 2,
                line,
                null,
                gameComponent);
        nextLine(image.getHeight(gameComponent));
    }

    private void nextLine(int heigh){
        line = line + heigh +20;
    }

    protected void setStartLine(){
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

}
