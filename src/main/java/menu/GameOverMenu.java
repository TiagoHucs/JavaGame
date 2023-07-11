package menu;

import game.GameComponent;
import game.GameState;
import game.SinglePlayerGameLogic;
import waves.WaveController;
import waves.WaveStatics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameOverMenu extends AbstractGameMenu {

    public GameOverMenu(GameComponent gameComponent) {
        super(gameComponent);
    }

    @Override
    public void init(GameComponent gameComponent) {
        setOptions(new String[]{"PRESS ESC TO BACK MENU"});
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        super.draw(g,gameComponent);

        WaveController waveController = ((SinglePlayerGameLogic) gameComponent.currentGameLogic).getWaveController();

        List<String> text = new ArrayList<>(4);
        text.add("GAME OVER");
        for (WaveStatics statics: waveController.getStatics()) {
            text.add("Wave: " + statics.getNumber());
            text.add("Points: " + statics.getPoints());
            text.add("Time: " + statics.getTimeToClean());
        }
        setText(text);
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
