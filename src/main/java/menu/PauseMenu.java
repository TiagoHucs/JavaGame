package menu;

import game.GameComponent;

public class PauseMenu extends AbstractGameMenu {

    public static final String OPT_RETOMAR_PARTIDA = "RETOMAR PARTIDA";
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
        this.options = defaultOptions;
        this.gameComponent = gameComponent;
    }

    @Override
    public void executeAction(String action) {
        switch (action) {
            case OPT_RETOMAR_PARTIDA:
                gameComponent.pause();
                gameComponent.initPlayers();
            case OPT_CONFIGURACOES:
                options = configOptions;
                break;
            case OPT_VOLTA_PRINCIPAL:
                options = defaultOptions;
                selectedOption = 0;
                break;
            case OPT_SAIR_DESKTOP:
                System.exit(0);
        }
    }

}
