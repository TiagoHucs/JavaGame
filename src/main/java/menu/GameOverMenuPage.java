package menu;

import game.GameComponent;
import game.GameState;
import game.SinglePlayerGameLogic;
import waves.WaveController;
import waves.WaveStatics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOverMenuPage extends AbstractMenuPage {

    public static final String OPT_RETURN_MENU = "PRESS ESC TO RETURN";

    public GameOverMenuPage(GameComponent gameComponent) {
        super("GAME OVER");
        setOptions(Arrays.asList(
                new AbstractMenuOption(OPT_RETURN_MENU) {
                    @Override
                    public void execute() {

                    }
                }
        ));
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

    }


}
