package menu;

import game.GameComponent;
import game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class IntroMenu extends AbstractGameMenu {
    private GameComponent gameComponent;

    private Graphics g;
    public IntroMenu(GameComponent gameComponent) {
        super(gameComponent);
        this.gameComponent = gameComponent;
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        this.g = g;
        this.write(Color.YELLOW,"INVADERS",40);
        this.write(Color.WHITE,"Press any key",20);
    }

    private void write(Color color,String text, int size){
        this.font = new Font("Arial", Font.PLAIN, size);
        this.g.setFont(font);
        this.metrics = this.g.getFontMetrics(font);
        int larguraTexto = metrics.stringWidth(text);
        int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
        int y = (gameComponent.getCfg().getAlturaTela() - larguraTexto) / 2;
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
