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

    private Image logo;

    public GameOverMenu(GameComponent gameComponent) {
        super(gameComponent);
        try {
            logo = ResourceManager.get().getImage("/image/gameover.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        setStartLine();

        WaveController waveController = ((SinglePlayerGameLogic) gameComponent.currentGameLogic).getWaveController();

        drawImage(g,logo);
        writeText(g, Color.YELLOW,"Statics",40);

        for (WaveStatics statics: waveController.getStatics()) {
            writeText(g, Color.CYAN,"Wave: " + statics.getNumber(),20);
            writeText(g, Color.CYAN,"Points: " + statics.getPoints(),20);
            writeText(g, Color.CYAN,"Time: " + statics.getTimeToClean(),20);
        }

        writeText(g, Color.WHITE,"Press esc to main",20);
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
