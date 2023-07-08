package game;

import effects.StarFieldEffect;

import lombok.SneakyThrows;
import menu.PauseMenu;
import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameComponent extends JComponent implements KeyListener, Runnable {
    private final int FPS_SET = 60;
    private final int UPS_SET = 58;
    private final Thread animationThread;
    private final Config cfg;
    private final StarFieldEffect starFieldEffect;
    private final Font font;
    private final SoundManager soundManager;
    public final GameLogic currentGameLogic, newPauseMenu;
    public final GameState gameState;

    public GameComponent(Config cfg) {
        this.cfg = cfg;
        this.font = new Font("TimesRoman", Font.PLAIN, 25);

        this.soundManager = new SoundManager();
        this.soundManager.loadSounds("/audio");

        this.gameState = new GameState();
        this.gameState.state = GameState.State.MENU;

        this.newPauseMenu = new PauseMenu(this);
        this.currentGameLogic = new SinglePlayerGameLogic();

        this.starFieldEffect = new StarFieldEffect(cfg.getLarguraTela(), cfg.getAlturaTela(), 400);

        this.animationThread = new Thread(this);
        this.animationThread.start();
    }

    public final SoundManager getSoundManager() {
        return soundManager;
    }

    public final Config getCfg() {
        return cfg;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, cfg.getLarguraTela(), cfg.getAlturaTela());

        starFieldEffect.draw(g);

        switch (gameState.state) {

            case MENU:
                newPauseMenu.draw(g, this);
                break;

            case PLAY:
                currentGameLogic.draw(g, this);
                break;

            case QUIT:
                System.exit(0);
                break;
        }

        g.dispose();
    }

    @SneakyThrows
    private void update() {

        switch (gameState.state) {

            case PLAY:
                currentGameLogic.update(this);
                break;

            case MENU:
                newPauseMenu.update(this);
                break;

            case QUIT:
                animationThread.sleep(1);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

        switch (gameState.state) {

            case PLAY:
                currentGameLogic.keyPressed(e);
                break;

            case MENU:
                newPauseMenu.keyPressed(e);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (gameState.state) {

            case PLAY:
                currentGameLogic.keyPressed(e);
                break;

            case MENU:
                newPauseMenu.keyPressed(e);
                break;
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

            soundManager.playSound("changing-tab.wav");

            if (GameState.State.MENU.equals(gameState.state)) {
                gameState.state = GameState.State.PLAY;

            } else {
                gameState.state = GameState.State.MENU;
            }

        }

        if (e.getKeyCode() == KeyEvent.VK_M) {
            cfg.setMuted(!cfg.isMuted());
            soundManager.toogleMute(cfg.isMuted());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (gameState.state) {
            case PLAY:
                currentGameLogic.keyReleased(e);
                break;

            case MENU:
                newPauseMenu.keyReleased(e);
                break;
        }

    }

    @SneakyThrows
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        while (gameState.isGameRunning()) {

            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                deltaU--;
            }

            if (deltaF >= 1) {
                repaint();
                deltaF--;
            }

            animationThread.sleep(10);
        }
    }
}
