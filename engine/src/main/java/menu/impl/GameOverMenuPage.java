package menu.impl;

import game.GameComponent;
import game.GameState;
import game.SinglePlayerGameLogic;
import menu.AbstractMenuOption;
import menu.AbstractMenuPage;
import menu.MainMenu;
import waves.WaveController;
import waves.WaveStatics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOverMenuPage extends AbstractMenuPage {

    public static final String OPT_RETURN_MENU = "PRESS ENTER TO RETURN";

    public GameOverMenuPage(String title, GameComponent gameComponent, MainMenu mainMenu) {
        super(title);
        setOptions(Arrays.asList(
                new AbstractMenuOption(OPT_RETURN_MENU) {
                    @Override
                    public void execute() {
                        gameComponent.gameState.state = GameState.State.MENU;
                        mainMenu.setMenuPage(MainMenu.KEY_GAME_MENU);
                    }
                }
        ));
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {
        super.draw(g, gameComponent);

        WaveController waveController = ((SinglePlayerGameLogic) gameComponent.currentGameLogic).getWaveController();

        List<String> text = new ArrayList<String>(
                waveController.getStatics().size() * 3);

        for (WaveStatics statics : waveController.getStatics()) {
            text.add(String.format("Wave: %02d", statics.getNumber()));
            text.add(String.format("Points: %012d", statics.getPoints()));
            text.add(String.format("Time: %.2f", statics.getTimeToClean()));
        }

        g.setColor(Color.LIGHT_GRAY);

        for (String txt : text) {
            int larguraTexto = metrics.stringWidth(txt);
            int x = (gameComponent.getCfg().getGameWidth() - larguraTexto) / 2;
            g.drawString(txt, x, line);
            nextLine(font.getSize());
        }

    }


}