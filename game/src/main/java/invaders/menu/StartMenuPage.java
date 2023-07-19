package menu.impl;

import game.GameComponent;
import game.GameState;
import menu.AbstractMenuOption;
import menu.AbstractMenuPage;
import menu.MainMenu;

import java.util.Arrays;

public class StartMenuPage extends AbstractMenuPage {

    public static final String OPT_BACK_GAME = "CONTINUE/RETURN GAME";
    private static final String OPT_CONFIGURATIONS = "CONFIGURATIONS";
    private static final String OPT_CREDITS = "GAME CREDITS";
    private static final String OPT_EXIT_GAME = "EXIT TO DESKTOP";

    public StartMenuPage(String title, GameComponent gameComponent, MainMenu mainMenu) {
        super(title, gameComponent);

        setOptions(Arrays.asList(
                new AbstractMenuOption(OPT_BACK_GAME) {
                    @Override
                    public void execute() {
                        gameComponent.gameState.state = GameState.State.PLAY;
                        gameComponent.currentGameLogic.init(gameComponent);
                    }
                },
                new AbstractMenuOption(OPT_CONFIGURATIONS) {
                    @Override
                    public void execute() {
                       mainMenu.setMenuPage(MainMenu.KEY_CONFIGURATIONS_MENU);
                    }
                },
                new AbstractMenuOption(OPT_CREDITS) {
                    @Override
                    public void execute() {
                        mainMenu.setMenuPage(MainMenu.KEY_CREDITS);
                    }
                },
                new AbstractMenuOption(OPT_EXIT_GAME) {
                    @Override
                    public void execute() {
                        gameComponent.gameState.state = GameState.State.QUIT;
                    }
                }

        ));
    }


}
