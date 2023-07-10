package menu;

import game.GameComponent;
import game.GameState;
import game.SinglePlayerGameLogic;
import utilities.ResourceManager;
import waves.WaveController;
import waves.WaveStatics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameOverMenu extends AbstractGameMenu {

    public GameOverMenu(GameComponent gameComponent) {
        super(gameComponent);
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        setStartLine();
        Image logo;
        WaveController waveController = ((SinglePlayerGameLogic) gameComponent.currentGameLogic).getWaveController();
        try {
            logo = ResourceManager.get().getImage("/image/gameover.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        drawImage(g,logo);
        write(g, Color.YELLOW,"Statics",40);

        for (WaveStatics statics: waveController.getStatics()) {
            this.write(g, Color.CYAN,"Wave: " + statics.getNumber(),20);
            this.write(g, Color.CYAN,"Points: " + statics.getPoints(),20);
            this.write(g, Color.CYAN,"Time: " + statics.getTimeToClean(),20);
        }

        this.write(g, Color.RED,"GAME OVER",40);
        this.write(g, Color.WHITE,"Press esc to main",20);
    }

    protected void write(Graphics g, Color color, String text, int size){

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
