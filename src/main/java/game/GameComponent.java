package game;

import effects.StarFieldEffect;
import lombok.SneakyThrows;
import menu.MainMenu;
import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameComponent extends JPanel implements KeyListener, Runnable {
    public static final float FPS_SET = 60.0f;
    private Config cfg;
    private SoundManager soundManager;
    private StarFieldEffect starFieldEffect;
    private Thread gameThread;
    public GameState gameState;
    public GameLogic currentGameLogic, mainMenu;

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
        this.soundManager.setGlobalVolume(cfg.getGlobalVolume());

        this.gameState = new GameState();
        this.gameState.state = GameState.State.MENU;

        this.currentGameLogic = new SinglePlayerGameLogic();

        this.mainMenu = new MainMenu(this);

        this.starFieldEffect = new StarFieldEffect(this, true);

        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        super.paintComponent(g2d);

        cfg.applyRenderScale(g2d);

        this.update();

        starFieldEffect.draw(g2d, this);

        switch (gameState.state) {

            case MENU:
                mainMenu.draw(g2d, this);
                break;
            case PLAY:
                currentGameLogic.draw(g2d, this);
                break;
            case GAMEOVER:
                ((MainMenu) mainMenu).setMenuPage(MainMenu.KEY_GAME_OVER);
                mainMenu.draw(g2d, this);
                break;
        }
    }

    @SneakyThrows
    public void update() {

        switch (gameState.state) {
            case PLAY:
                currentGameLogic.update(this);
                break;
            case MENU:
            case GAMEOVER:
                mainMenu.update(this);
                break;
            case QUIT:
                System.exit(0);
                gameThread.join();
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
            case GAMEOVER:
                mainMenu.keyPressed(e);
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
            case GAMEOVER:
                mainMenu.keyReleased(e);
                break;
        }

    }

    @Override
    public void run() {

        final float timePerFrame = 1000000000.0f / FPS_SET;

        long previousTime = System.nanoTime();

        float delta = 0.0f;

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
