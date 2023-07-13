package menu;

import game.GameComponent;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public abstract class AbstractMenuPage implements MenuPage {

    protected static final String OPT_BACK = "BACK";

    private String title;
    private List<MenuOption> options;
    private MenuOption selectedOption;
    protected final Font font = ResourceManager.get().getFont();
    protected FontMetrics metrics;
    protected int line = 0;
    private final Image logo;

    public AbstractMenuPage(String title) {
        this.title = title;
        try {
            logo = ResourceManager.get().getImage("/image/logo.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setOptions(List<MenuOption> options){
        this.options = options;
        this.selectedOption = options.get(0);
    }

    public void draw(Graphics g, GameComponent gameComponent) {
        setStartLine(gameComponent);
        drawGameLogo(g, gameComponent);
        g.setFont(font);
        drawTitle(g,gameComponent);
        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }
        drawOptions(g, gameComponent);
    }

    protected void drawOptions(Graphics g, GameComponent gameComponent) {
        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }
        for (MenuOption option: options) {
            g.setColor(option == selectedOption ? Color.YELLOW : Color.WHITE);
            int larguraTexto = metrics.stringWidth(option.getTitle());
            int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
            g.drawString(option.getTitle(), x, line);
            nextLine(16);//TODO: pegar automaticamente o tamanho da fonte
        }
    }

    protected void drawTitle(Graphics g, GameComponent gameComponent) {
        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        g.setColor(Color.BLUE);
        int larguraTexto = metrics.stringWidth(this.title);
        int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
        g.drawString(this.title, x, line);
        nextLine(16);//TODO: pegar automaticamente o tamanho da fonte

    }

    private void drawGameLogo(Graphics g, GameComponent gameComponent){
        g.drawImage(logo,
                gameComponent.getCfg().getLarguraTela()/2 - logo.getWidth(gameComponent) / 2,
                gameComponent.getCfg().getAlturaTela()/5,
                null,
                gameComponent);
        nextLine(logo.getHeight(gameComponent)/2);
    }

    protected void nextLine(int heigh){
        line = line + heigh +20;
    }

    private void setStartLine(GameComponent gameComponent){
        line = gameComponent.getCfg().getAlturaTela()/3;
    }

    public void sendEvent(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            int index = options.indexOf(selectedOption);
            if (index > 0) {
                MenuOption beforeOption = options.get(index - 1);
                selectedOption = beforeOption;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            int index = options.indexOf(selectedOption);
            if (index < options.size() - 1) {
                MenuOption nextOption = options.get(index + 1);
                selectedOption = nextOption;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            selectedOption.execute();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(selectedOption instanceof MenuOptionLevel){
                ((MenuOptionLevel)selectedOption).decrease();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(selectedOption instanceof MenuOptionLevel){
                ((MenuOptionLevel)selectedOption).increase();
            }
        }
    }


}
