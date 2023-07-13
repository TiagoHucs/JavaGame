package menu.impl;

import game.GameComponent;
import game.GameState;
import menu.AbstractMenuOption;
import menu.AbstractMenuPage;
import menu.MainMenu;
import menu.MenuPage;

import java.util.Arrays;

public class StartMenuPage extends AbstractMenuPage {

    public static final String OPT_BACK_GAME = "INICIAR/RETOMAR PARTIDA";
    private static final String OPT_CONFIGURATIONS = "CONFIGURAÇÕES";
    private static final String OPT_EXIT_GAME = "SAIR PARA DESKTOP";

    public StartMenuPage(String title, GameComponent gameComponent, MainMenu mainMenu) {
        super(title);

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
                       mainMenu.setMenuPage("CONFIGURATIONS MENU");
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
