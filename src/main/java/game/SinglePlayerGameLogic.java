package game;

import entities.Enemy;
import entities.Shot;
import utilities.Config;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class SinglePlayerGameLogic implements GameLogic {

    private final int ENEMY_COUNT = 20;
    private final int PLAYER_COUT = 7;

    private final Colisor colisor = new Colisor();
    private Set<PlayerState> players = new HashSet<PlayerState>(PLAYER_COUT);
    private final Set<Enemy> enemies = new HashSet<Enemy>(ENEMY_COUNT);

    @Override
    public void init() {

        if (players.size() < PLAYER_COUT) {
            players.add(new PlayerState(players.size()));
        }

        for (PlayerState player: players) {
            player.getBlinkEffect().reset();
        }
    }

    @Override
    public void draw(Graphics g, GameComponent gameComponent) {

        for (Enemy i : enemies) {
            i.draw(g, gameComponent);
        }

        for (PlayerState playerState : players) {
            playerState.draw(g, gameComponent);
        }
    }

    @Override
    public void update(GameComponent gameComponent) {

        // Se acabarem os inimigos gere mais
        if (enemies.isEmpty()) {
            geraInimigos(gameComponent);
        }

        for (Enemy inimigo : enemies) {
            inimigo.move();
            inimigo.clampMove(gameComponent.getCfg());
        }

        for (PlayerState playerState : players) {
            playerState.update(gameComponent);
        }

        checkCollisions(gameComponent);
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
                }

                playerState.getBullets().removeAll(listaTirosDestruidos);
            }
        }

        enemies.removeAll(listaInimigosDestruidos);
    }

    private void geraInimigos(GameComponent gameComponent) {

        Config cfg = gameComponent.getCfg();
        SoundManager soundManager = gameComponent.getSoundManager();

        for (int i = 0; i < ENEMY_COUNT; i++) {
            enemies.add(new Enemy(cfg.getRandomGenerator().nextInt(cfg.getResolution()), i * -50));
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
