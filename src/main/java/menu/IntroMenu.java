package menu;

import game.GameComponent;
import game.GameState;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class IntroMenu extends AbstractGameMenu {
    private GameComponent gameComponent;
    private Image logo;

    private Graphics g;
    public IntroMenu(GameComponent gameComponent) {
        super(gameComponent);
        this.gameComponent = gameComponent;
        try {
            this.logo = ResourceManager.get().getImage("/image/logo.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        this.g = g;
        int x = (gameComponent.getCfg().getLarguraTela() - logo.getWidth(gameComponent)) / 2;
        this.g.drawImage(logo,x, gameComponent.getCfg().getAlturaTela()/3, null,gameComponent);
        this.write(Color.WHITE,"Press any key",20, gameComponent.getCfg().getAlturaTela()-200);
    }

    private void write(Color color,String text, int size, int y){
        this.font = new Font("Arial", Font.PLAIN, size);
        this.g.setFont(font);
        this.metrics = this.g.getFontMetrics(font);
        int larguraTexto = metrics.stringWidth(text);
        int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
        this.g.setColor(color);
        this.g.drawString(text, x, y);
    }

    @Override
    public void executeAction(String action) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameComponent.gameState.state = GameState.State.MENU;
    }

    @Override
    public void update(GameComponent gameComponent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
