package menu;

import game.GameComponent;
import game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverMenu extends AbstractGameMenu {
    private GameComponent gameComponent;

    private Graphics g;
    private int line = 0;
    public GameOverMenu(GameComponent gameComponent) {
        super(gameComponent);
        this.gameComponent = gameComponent;
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {

        this.g = g;
        this.line = 0;
        this.write(Color.YELLOW,"GAME OVER",40);
        this.write(Color.WHITE,"1st 999999",20);
        this.write(Color.WHITE,"2st 888888",20);
        this.write(Color.WHITE,"3st 777777",20);
        this.write(Color.WHITE,"Press esc to main",20);

    }

    private void write(Color color,String text, int size){
        this.font = new Font("Arial", Font.PLAIN, size);
        this.g.setFont(font);
        this.metrics = this.g.getFontMetrics(font);
        int larguraTexto = metrics.stringWidth(text);
        int x = (gameComponent.getCfg().getLarguraTela() - larguraTexto) / 2;
        int y = (gameComponent.getCfg().getAlturaTela()) / 2;
        this.g.setColor(color);
        this.g.drawString(text, x, y + (size * line));
        this.line++;
    }

    @Override
    public void executeAction(String action) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(KeyEvent.VK_ESCAPE == e.getKeyCode()){
            gameComponent.gameState.state = GameState.State.INTRO;
        }
    }

    @Override
    public void update(GameComponent gameComponent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
