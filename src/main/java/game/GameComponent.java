package game;

import effects.StarFieldEffect;

import menu.PauseMenu;
import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameComponent extends JPanel implements KeyListener, Runnable {
    private final int FPS_SET = 60;
    private Config cfg;
    private SoundManager soundManager;
    private StarFieldEffect starFieldEffect;
    private Thread gameThread;
    public GameState gameState;
    public GameLogic currentGameLogic, newPauseMenu;

    public final SoundManager getSoundManager() {
        return soundManager;
    }

    public final Config getCfg() {
        return cfg;
    }

    public GameComponent(Config cfg) {
        this.cfg = cfg;
        this.cfg.setup(this);
        this.init();
    }

    public void init() {
        this.soundManager = new SoundManager();
        this.soundManager.loadSounds("/audio");
        this.soundManager.setGlobalVolume(0.5f);

        this.gameState = new GameState();
        this.gameState.state = GameState.State.MENU;

        this.newPauseMenu = new PauseMenu(this);
        this.currentGameLogic = new SinglePlayerGameLogic();

        this.starFieldEffect = new StarFieldEffect(cfg.getLarguraTela(), cfg.getAlturaTela(), 400);

        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        this.update();

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
        }

    }

    public void update() {

        switch (gameState.state) {

            case PLAY:
                currentGameLogic.update(this);
                break;

            case MENU:
                newPauseMenu.update(this);
                break;

            case QUIT:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nao faz nada
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_M:
                cfg.setMuted(!cfg.isMuted());
                soundManager.toogleMute(cfg.isMuted());
                break;

            case KeyEvent.VK_ESCAPE:
                gameState.state = GameState.State.MENU;
                soundManager.playSound("changing-tab.wav");
                break;

        }

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

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;

        long previousTime = System.nanoTime();

        double delta = 0;

        while (gameState.isGameRunning()) {

            long currentTime = System.nanoTime();

            delta += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (delta >= 1) {
                repaint();
                delta--;
            }

        }
    }
}
