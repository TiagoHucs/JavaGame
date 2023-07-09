package menu;

import game.GameComponent;
import game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class IntroMenu extends AbstractGameMenu {
    private GameComponent gameComponent;

    public IntroMenu(GameComponent gameComponent) {
        super(gameComponent);
        this.gameComponent = gameComponent;
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {

        g.setColor(Color.YELLOW);

        this.font = new Font("Arial", Font.PLAIN, 40);
        g.setFont(font);

        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        String text = "INVADERS";
        int larguraTexto = metrics.stringWidth(text);
        int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
        int y = (gameComponent.getCfg().getAlturaTela() - larguraTexto) / 2;
        g.drawString(text, x, y);

        text = "Press any key";
        g.setColor(Color.WHITE);

        this.font = new Font("Arial", Font.PLAIN, 20);
        g.setFont(font);

        if (metrics == null) {
            metrics = g.getFontMetrics(font);
        }

        larguraTexto = metrics.stringWidth(text);
        x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
        g.drawString(text, x, y + 20);
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
