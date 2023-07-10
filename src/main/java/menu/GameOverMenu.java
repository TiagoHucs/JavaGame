package menu;

import game.GameComponent;
import game.GameState;
import game.SinglePlayerGameLogic;
import waves.WaveController;
import waves.WaveStatics;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverMenu extends AbstractGameMenu {
    private int line = 0;

    public GameOverMenu(GameComponent gameComponent) {
        super(gameComponent);
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {

        this.line = 0;

        WaveController waveController = ((SinglePlayerGameLogic) gameComponent.currentGameLogic).getWaveController();

        this.write(g, Color.YELLOW,"Statics",40);

        for (WaveStatics statics: waveController.getStatics()) {
            this.write(g, Color.CYAN,"Wave: " + statics.getNumber(),20);
            this.write(g, Color.CYAN,"Points: " + statics.getPoints(),20);
            this.write(g, Color.CYAN,"Time: " + statics.getTimeToClean(),20);
        }

        this.write(g, Color.RED,"GAME OVER",40);
        this.write(g, Color.WHITE,"Press esc to main",20);
    }

    private void write(Graphics g, Color color, String text, int size){

        font = new Font("Arial", Font.PLAIN, size);
        metrics = g.getFontMetrics(font);

        g.setFont(font);
        g.setColor(color);

        int larguraTexto = metrics.stringWidth(text);
        int x = (gameComponent.getWidth() - larguraTexto) / 2;
        int y = (gameComponent.getHeight()) / 2;

        g.drawString(text, x, y + (size * line));
        line++;
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
