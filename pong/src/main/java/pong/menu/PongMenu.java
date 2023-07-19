package pong.menu;

import game.GameComponent;
import game.GameState;
import menu.AbstractMenu;
import menu.AbstractMenuOption;
import menu.AbstractMenuPage;

import java.util.Arrays;

public class PongMenu extends AbstractMenu {

    public PongMenu(GameComponent gameComponent) {
        registerMenu(criaPaginaUnica(gameComponent));
        setMenuPage("KEY_PONG_MENU_PAGE");
    }

    private AbstractMenuPage criaPaginaUnica(GameComponent gameComponent) {

        AbstractMenuPage pongMenuPage = new AbstractMenuPage(gameComponent) {
            @Override
            public String getKey() {
                return "KEY_PONG_MENU_PAGE";
            }
        };

        pongMenuPage.setOptions(Arrays.asList(
                new AbstractMenuOption("Back Game") {
                    @Override
                    public void execute() {
                        gameComponent.gameState.state = GameState.State.PLAY;
                        gameComponent.currentGameLogic.init(gameComponent);
                    }
                },
                new AbstractMenuOption("Exit Game") {
                    @Override
                    public void execute() {
                        gameComponent.gameState.state = GameState.State.QUIT;
                    }
                }
        ));
        return pongMenuPage;
    }

    @Override
    public void init(GameComponent gameComponent) {

    }
}
