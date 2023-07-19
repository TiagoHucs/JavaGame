package menu;

import game.GameComponent;
import lombok.SneakyThrows;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class AbstractMenuPage implements MenuPage {

    protected static final String OPT_BACK = "BACK";
    private String title;
    private List<MenuOption> options;
    private MenuOption selectedOption;
    protected final Font font = ResourceManager.get().getFont();
    protected FontMetrics metrics;
    protected int line = 0;
    private final BufferedImage logo;

    protected final GameComponent gameComponent;

    @SneakyThrows
    public AbstractMenuPage(GameComponent gameComponent) {
        this.gameComponent = gameComponent;
        this.logo = ResourceManager.get().getImage("/image/logo.png");
    }

    public abstract String getKey();

    protected void setOptions(List<MenuOption> options) {
        this.options = options;
        this.selectedOption = options.get(0);
    }

    public void draw(Graphics g) {

        g.setFont(font);

        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        setStartLine(gameComponent);
        drawGameLogo(g, gameComponent);
       // drawTitle(g, gameComponent);
        if(haveOptions()){
            drawOptions(g, gameComponent);
        }
    }

    private boolean haveOptions() {
        return options != null && !options.isEmpty();
    }

    protected void drawOptions(Graphics g, GameComponent gameComponent) {

        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        int w = gameComponent.getCfg().getGameWidth();

        for (MenuOption option : options) {
            g.setColor(option == selectedOption ? Color.YELLOW : Color.WHITE);
            int larguraTexto = metrics.stringWidth(option.getTitle());
            int x = (w - larguraTexto) / 2;
            g.drawString(option.getTitle(), x, line);
            nextLine(font.getSize());
        }
    }

    protected void drawTitle(Graphics g, GameComponent gameComponent) {

        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        int w = gameComponent.getCfg().getGameWidth();
        int larguraTexto = metrics.stringWidth(this.title);

        g.setColor(Color.BLUE);
        int x = (w - larguraTexto) / 2;
        g.drawString(this.title, x, line);
        nextLine(font.getSize());
    }

    protected void drawText(Graphics g, GameComponent gameComponent, List<String> text) {

        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        int w = gameComponent.getCfg().getGameWidth();

        g.setColor(Color.WHITE);
        for (String textLine : text) {
            int textWidth = metrics.stringWidth(textLine);
            int x = (w - textWidth) / 2;
            g.drawString(textLine, x, line);
            nextLine(font.getSize());
        }
    }

    private void drawGameLogo(Graphics g, GameComponent gameComponent) {

        int w = gameComponent.getCfg().getGameWidth();
        int h = gameComponent.getCfg().getGameHeight();

        g.drawImage(logo,
                w / 2 - logo.getWidth() / 2,
                h / 5,
                null,
                null);

        nextLine(logo.getHeight() / 2);
    }

    protected void nextLine(int heigh) {
        line = line + heigh + 20;
    }

    protected void setStartLine(GameComponent gameComponent) {
        line = gameComponent.getCfg().getGameHeight() / 3;
    }

    public void sendEvent(KeyEvent e) {
        if(haveOptions()){
            moveBetweenOptions(e);

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                selectedOption.execute();
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (selectedOption instanceof MenuOptionLevel) {
                    ((MenuOptionLevel) selectedOption).decrease();
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (selectedOption instanceof MenuOptionLevel) {
                    ((MenuOptionLevel) selectedOption).increase();
                }
            }
        }


    }

    private void moveBetweenOptions(KeyEvent e) {
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
    }

    public String getTitle() {
        return title;
    }

}
