package game;

import entities.Enemy;
import entities.PowerUp;
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
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SinglePlayerGameLogic implements GameLogic {
    private final int PLAYER_COUT = 7;
    private List<PlayerState> players = new LinkedList<PlayerState>();
    private List<Enemy> enemies = new LinkedList<Enemy>();
    private List<Explosion2> explosions = new LinkedList<Explosion2>();
    private List<PowerUp> powerUps = new LinkedList<PowerUp>();
    private BehaviorIA[] behaviorIA;
    private WaveController waveController;
    private ScoreAnimation scoreAnimation = new ScoreAnimation();
    private CollisionController collisionController = new CollisionController();

    public final WaveController getWaveController() {
        return waveController;
    }

    @Override
    public void init(GameComponent gameComponent) {

        if (players.size() < PLAYER_COUT) {
            PlayerState playerState = new PlayerState(players.size());
            Ship ship = playerState.getShip();
            ship.setPosition(new Point2D.Float(
                    ship.getHorizonntalLimit(gameComponent) / 2.0f,
                    ship.getVerticalLimit(gameComponent) - ship.getSize().y));
            players.add(playerState);
        }

        for (PlayerState player: players) {
            player.getActions().reset();
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

        for (PowerUp powerUp : powerUps) {
            powerUp.drawImage(g, gameComponent);
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
        updatePowerUps(gameComponent);
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
            inimigo.damage(players, gameComponent);
            inimigo.clampMove(gameComponent);
        }

    }

    private void updatePowerUps(GameComponent gameComponent) {

        Iterator<PowerUp> powerUpItr = powerUps.iterator();

        while (powerUpItr.hasNext()) {

            PowerUp powerUp = powerUpItr.next();

            powerUp.move();

            for (PlayerState playerState : players) {

                if (collisionController.detectaColisao(powerUp, playerState.getShip())) {
                    powerUp.levelUp(playerState, scoreAnimation);
                    gameComponent.getSoundManager().playSound("power-up.wav");
                    powerUpItr.remove();
                    break;
                }

            }

            if (powerUp.isOutOfScreen(gameComponent)) {
                powerUpItr.remove();
            }
        }
    }

    private void checkCollisions(GameComponent gameComponent) {

        for (PlayerState playerState : players) {

            Iterator<Enemy> enemyIterator = enemies.iterator();

            while (enemyIterator.hasNext()) {
                Enemy inimigo = enemyIterator.next();
                verificaColisao(gameComponent, enemyIterator, inimigo, playerState);
                verificaVidaInimigo(gameComponent, enemyIterator, inimigo);
            }

        }
    }

    private void verificaVidaInimigo(GameComponent gc, Iterator<Enemy> enemyIterator, Enemy inimigo) {
        if (inimigo.getLifes() == 0) {
            explosions.add(new Explosion2(inimigo));
            gc.getSoundManager().playSound("explosion.wav");
            enemyIterator.remove();
        }
    }

    private void verificaColisao(PlayerState playerState, Enemy inimigo) {

        Iterator<Shot> bullerIterator = playerState.getBullets().iterator();

        while (bullerIterator.hasNext()) {

            Shot tiro = bullerIterator.next();

            if (collisionController.detectaColisao(tiro, inimigo)) {

                if (powerUps.isEmpty() && inimigo.isDropPowerUp()) {
                    PowerUp powerUp = new PowerUp(inimigo);
                    powerUps.add(powerUp);
                }

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

        if (collisionController.detectaColisao(playerState.getShip(), inimigo)) {

            playerState.getShip().sofreDano(25);
            gameComponent.getSoundManager().playSound("im-hit.wav");

            if (playerState.getShootsPerFire() > 1) {
                PowerUp powerUp = new PowerUp(playerState.getShip());
                powerUp.levelDown(playerState, scoreAnimation);
                powerUps.add(powerUp);
            }

            explosions.add(new Explosion2(inimigo));
            enemyIterator.remove();

        } else {
            verificaColisao(playerState, inimigo);
        }

    }

    private void geraInimigos(GameComponent gameComponent) {

        Config cfg = gameComponent.getCfg();
        SoundManager soundManager = gameComponent.getSoundManager();
        Dimension offset = new Dimension(cfg.getGameWidth() / 2, 0);

        int typeIA = cfg.getRandomGenerator().nextInt(behaviorIA.length);
        BehaviorIA enemyIA = behaviorIA[typeIA];

        enemies = waveController.getWaveLayout().layoutToEnemy(offset.width, offset.height, enemyIA, typeIA, gameComponent);

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
