package invaders.menu;

import game.GameComponent;
import game.GameState;
import menu.AbstractMenuOption;
import menu.AbstractMenuPage;
import menu.AbstractMenu;

import java.util.Arrays;

public class StartMenuPage extends AbstractMenuPage {

    public static final String KEY = "KEY_START_MENU";

    public static final String OPT_BACK_GAME = "CONTINUE/RETURN GAME";
    private static final String OPT_CONFIGURATIONS = "CONFIGURATIONS";
    private static final String OPT_CREDITS = "GAME CREDITS";
    private static final String OPT_EXIT_GAME = "EXIT TO DESKTOP";

    public StartMenuPage(GameComponent gameComponent, AbstractMenu menu) {
        super( gameComponent);

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
                       menu.setMenuPage(ConfigMenuPage.KEY);
                    }
                },
                new AbstractMenuOption(OPT_CREDITS) {
                    @Override
                    public void execute() {
                        menu.setMenuPage(CreditsMenuPage.KEY);
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


    @Override
    public String getKey() {
        return KEY;
    }
}
