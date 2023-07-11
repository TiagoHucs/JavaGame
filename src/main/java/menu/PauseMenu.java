package menu;

import game.GameComponent;
import game.GameState;

import java.awt.event.KeyEvent;

public class PauseMenu extends AbstractGameMenu {

    public static final String OPT_RETOMAR_PARTIDA = "INICIAR/RETOMAR PARTIDA";
    private static final String OPT_CONFIGURACOES = "CONFIGURAÇÕES";
    private static final String OPT_SAIR_DESKTOP = "SAIR PARA DESKTOP";
    private static final String OPT_SOM = "CONFIGURAÇÕES SOM";
    private static final String OPT_DIFICULDADE = "DIFICULDADE DO JOGO";
    private static final String OPT_CONTROLE = "CONFIGURAÇÕES DE CONTROLE";
    private static final String OPT_VOLTA_PRINCIPAL = "VOLTAR PARA O MENU PRINCIPAL";

    private final String[] defaultOptions = {OPT_RETOMAR_PARTIDA, OPT_CONFIGURACOES, OPT_SAIR_DESKTOP};
    private final String[] configOptions = {OPT_SOM, OPT_DIFICULDADE, OPT_CONTROLE, OPT_VOLTA_PRINCIPAL};

    private GameComponent gameComponent;

    public PauseMenu(GameComponent gameComponent) {
        super(gameComponent);
        this.gameComponent = gameComponent;
        this.setOptions(defaultOptions);
    }

    @Override
    public void executeAction(String action) {
        switch (action) {
            case OPT_RETOMAR_PARTIDA:
                gameComponent.gameState.state = GameState.State.PLAY;
                gameComponent.currentGameLogic.init(gameComponent);
                break;
            case OPT_CONFIGURACOES:
                setOptions(configOptions);
                break;
            case OPT_VOLTA_PRINCIPAL:
                setOptions(defaultOptions);
                break;
            case OPT_SAIR_DESKTOP:
                gameComponent.gameState.state = GameState.State.QUIT;
                break;
        }
    }

    @Override
    public void update(GameComponent gameComponent) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
