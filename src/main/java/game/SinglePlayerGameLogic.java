package game;

import entities.Enemy;
import entities.Ship;
import entities.Shot;
import ia.BehaviorIA;
import ia.FallDownIA;
import ia.LeftRightIA;
import ps.Explosion2;
import utilities.Config;
import waves.WaveController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class SinglePlayerGameLogic implements GameLogic {
    private final int PLAYER_COUT = 7;

    private final Colisor colisor = new Colisor();
    private List<PlayerState> players = new LinkedList<PlayerState>();
    private List<Enemy> enemies = new LinkedList<Enemy>();
    private List<Explosion2> explosions = new LinkedList<Explosion2>();
    private BehaviorIA[] behaviorIA;
    private WaveController waveController;

    private ScoreAnimation scoreAnimation = new ScoreAnimation();

    public final WaveController getWaveController() {
        return waveController;
    }

    @Override
    public void init(GameComponent gameComponent) {

        if (players.size() < PLAYER_COUT) {
            PlayerState playerState = new PlayerState(players.size());
            Ship ship = playerState.getShip();
            ship.setX(ship.getHorizonntalLimit(gameComponent) / 2);
            ship.setY(ship.getVerticalLimit(gameComponent) - ship.getAltura());
            players.add(playerState);
        }

        for (PlayerState player: players) {
            player.getBlinkEffect().reset();
        }

        // Cria os comportamentos possiveis para os inimigos
        behaviorIA = new BehaviorIA[2];
        behaviorIA[0] = new FallDownIA();
        behaviorIA[1] = new LeftRightIA();

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

        Iterator<Explosion2> explosionItr = explosions.iterator();

        while (explosionItr.hasNext()) {

            Explosion2 explosion = explosionItr.next();

            explosion.update(g, gameComponent);

            if (explosion.isFinished()) {
                explosionItr.remove();
            }
        }

        scoreAnimation.draw(g, gameComponent);

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

                for (PlayerState playerState : players) {
                    waveController.updateCurrentWaveStatics(playerState);
                    playerState.levelUp();
                }

                waveController.nextWave();

            }  else if (waveController.isCanCreateEnemyWave()) {
                geraInimigos(gameComponent);
            }

            waveController.finishCurrentWave();
        }

        waveController.updateStatics();
    }

    private void checkForGameOver(GameComponent gameComponent) {

        Iterator<PlayerState> playerStateIterator = players.iterator();

        while (playerStateIterator.hasNext()) {

            PlayerState playerStat = playerStateIterator.next();

            if (playerStat.getShip().getLifes() == 0) {
                waveController.updateCurrentWaveStatics(playerStat);
                playerStateIterator.remove();
            }
        }

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

        for (PlayerState playerState : players) {

            Iterator<Enemy> enemyIterator = enemies.iterator();

            while (enemyIterator.hasNext()) {
                Enemy inimigo = enemyIterator.next();
                verificaColisao(gameComponent, enemyIterator, inimigo, playerState);
                verificaVidaInimigo(enemyIterator, inimigo);
            }

        }
    }

    private void verificaVidaInimigo(Iterator<Enemy> enemyIterator, Enemy inimigo) {
        if (inimigo.getLifes() == 0) {
            explosions.add(new Explosion2(inimigo));
            enemyIterator.remove();
        }
    }

    private void verificaColisao(PlayerState playerState, Enemy inimigo) {

        Iterator<Shot> bullerIterator = playerState.getBullets().iterator();

        while (bullerIterator.hasNext()) {

            Shot tiro = bullerIterator.next();

            if (colisor.detectaColisao(tiro, inimigo)) {

                inimigo.removeLifes(1);
                playerState.addScore(100);

                if (playerState.extraLife()) {
                    playerState.addExtraLife(1);
                    scoreAnimation.addExtraLife(inimigo);

                } else {
                    scoreAnimation.addScore(100, inimigo);
                }

                bullerIterator.remove();
            }
        }
    }

    private void verificaColisao(GameComponent gameComponent, Iterator<Enemy> enemyIterator, Enemy inimigo, PlayerState playerState) {

        // Se jogador invencivel, tiros tmb nao tem efeito no inimigo.
        if (playerState.getBlinkEffect().isInvencible())
            return;

        if (colisor.detectaColisao(playerState.getShip(), inimigo)) {

            playerState.getShip().sofreDano(25);
            explosions.add(new Explosion2(inimigo));
            gameComponent.getSoundManager().playSound("im-hit.wav");
            enemyIterator.remove();

        } else {
            verificaColisao(playerState, inimigo);
        }

    }

    private void geraInimigos(GameComponent gameComponent) {

        Config cfg = gameComponent.getCfg();
        SoundManager soundManager = gameComponent.getSoundManager();

        int typeIA = cfg.getRandomGenerator().nextInt(behaviorIA.length);
        BehaviorIA enemyIA = behaviorIA[typeIA];

        enemies = waveController.getWaveLayout()
                .layoutToEnemy(gameComponent.getWidth() / 2, 0, enemyIA, gameComponent);

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
