package game;

import entities.Enemy;
import entities.Shot;
import ia.BehaviorIA;
import ia.FallDownIA;
import ia.FallSideIA;
import ia.LeftRightIA;
import ps.Explosion;
import utilities.Config;
import waves.WaveController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class SinglePlayerGameLogic implements GameLogic {

    private final int ENEMY_COUNT = 20;
    private final int PLAYER_COUT = 7;

    private final Colisor colisor = new Colisor();
    private Set<PlayerState> players = new HashSet<PlayerState>(PLAYER_COUT);
    private final Set<Enemy> enemies = new HashSet<Enemy>(ENEMY_COUNT);
    private BehaviorIA[] behaviorIA;
    private WaveController waveController;

    @Override
    public void init() {

        if (players.size() < PLAYER_COUT) {
            players.add(new PlayerState(players.size()));
        }

        for (PlayerState player: players) {
            player.getBlinkEffect().reset();
        }

        // Cria os comportamentos possiveis para os inimigos
        behaviorIA = new BehaviorIA[3];
        behaviorIA[0] = new FallDownIA();
        behaviorIA[1] = new FallSideIA();
        behaviorIA[2] = new LeftRightIA();

        this.waveController = new WaveController();
        this.waveController.init();

        // Limpa enimigos
        this.enemies.clear();
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {

        for (Enemy i : enemies) {
            i.draw(g, gameComponent);
        }

        for (PlayerState playerState : players) {
            playerState.draw(g, gameComponent);
        }

        waveController.draw(g, gameComponent);
    }

    @Override
    public void update(GameComponent gameComponent) {
        updateWaveController(gameComponent);
        updateInimigos(gameComponent);
        updatePlayers(gameComponent);
        checkCollisions(gameComponent);
        checkForGameOver(gameComponent);
    }

    private void updateWaveController(GameComponent gameComponent) {

        if (enemies.isEmpty()) {

            if (waveController.goToNextWave()) {
                waveController.nextWave();

            }  else if (waveController.isCanCreateEnemyWave()) {
                geraInimigos(gameComponent);
            }

            waveController.finishCurrentWave();
        }

        waveController.updateStatics();
    }

    private void checkForGameOver(GameComponent gameComponent) {

        List<PlayerState> playersToRemove = new ArrayList<PlayerState>(players.size());

        for (PlayerState playerState : players) {
            if (playerState.getShip().getLifes() == 0) {
                playersToRemove.add(playerState);
                waveController.updateCurrentWaveStatics(playerState);
            }
        }

        players.removeAll(playersToRemove);

        if (players.isEmpty()) {
            gameComponent.gameState.state = GameState.State.GAMEOVER;
        }
    }

    private void updatePlayers(GameComponent gameComponent) {

        for (PlayerState playerState : players) {
            playerState.update(gameComponent);
        }

    }

    private void updateInimigos(GameComponent gameComponent) {

        for (Enemy inimigo : enemies) {
            inimigo.move();
            inimigo.clampMove(gameComponent);
        }

    }

    private void checkCollisions(GameComponent gameComponent) {

        SoundManager soundManager = gameComponent.getSoundManager();

        Set<Enemy> listaInimigosDestruidos = new LinkedHashSet<Enemy>(enemies.size());

        for (Enemy inimigo : enemies) {

            for (PlayerState playerState : players) {

                Set<Shot> listaTirosDestruidos = new HashSet<Shot>();

                for (Shot tiro : playerState.getBullets()) {
                    if (tiro.getY() < -10) {
                        listaTirosDestruidos.add(tiro);

                    } else if (colisor.detectaColisao(tiro, inimigo)) {
                        listaTirosDestruidos.add(tiro);

                        if (inimigo.getLifes() == 0){
                            listaInimigosDestruidos.add(inimigo);
                            playerState.getExplosions().add(new Explosion(inimigo));

                        } else {
                            inimigo.setLifes(inimigo.getLifes()-1);
                        }

                        playerState.addScore(100);
                    }
                }

                if (!playerState.getBlinkEffect().isInvencible() && colisor.detectaColisao(playerState.getShip(), inimigo)) {
                    playerState.getShip().sofreDano(25);
                    soundManager.playSound("im-hit.wav");

                    listaInimigosDestruidos.add(inimigo);
                    playerState.getExplosions().add(new Explosion(inimigo));
                }

                playerState.getBullets().removeAll(listaTirosDestruidos);
            }
        }

        enemies.removeAll(listaInimigosDestruidos);
    }

    private void geraInimigos(GameComponent gameComponent) {

        Config cfg = gameComponent.getCfg();
        SoundManager soundManager = gameComponent.getSoundManager();

        int typeIA = cfg.getRandomGenerator().nextInt(behaviorIA.length);
        BehaviorIA enemyIA = behaviorIA[typeIA];

        for (int i = 0; i < ENEMY_COUNT; i++) {
            Enemy enemy = new Enemy(enemyIA);
            enemyIA.setupEnemy(i, enemy, gameComponent);
            enemies.add(enemy);
        }

        soundManager.playSound("fighters-coming.wav");
    }

    @Override
    public void keyPressed(KeyEvent e) {

        for (PlayerState playerState : players) {
            playerState.getActions().keyPressed(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        for (PlayerState playerState : players) {
            playerState.getActions().keyReleased(e);
        }

    }


}
