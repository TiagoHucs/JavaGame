package game;

import effects.StarFieldEffect;
import engine.GameRender;
import menu.MainMenu;
import utilities.Config;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class GameComponent implements KeyListener {
    private Config cfg;
    private SoundManager soundManager;
    private StarFieldEffect starFieldEffect;
    public GameState gameState;
    public GameLogic currentGameLogic;
    public MainMenu mainMenu;

    public final SoundManager getSoundManager() {
        return soundManager;
    }

    public final Config getCfg() {
        return cfg;
    }

    public void setConfig(Config config) {
        this.cfg = config;
    }

    public void init() {
        this.soundManager = new SoundManager();
        this.soundManager.loadSounds("/audio");
        this.soundManager.setGlobalVolume(cfg.getGlobalVolume());
        this.gameState = new GameState();
        this.gameState.state = GameState.State.MENU;
        this.currentGameLogic = new SinglePlayerGameLogic(this);
        this.mainMenu = new MainMenu(this);
        this.starFieldEffect = new StarFieldEffect(this, true);
    }

    public void update(float delta) {

        switch (gameState.state) {
            case MENU:
                mainMenu.update(this, delta);
                break;
            case PLAY:
                currentGameLogic.update(this, delta);
                break;
            case GAMEOVER:
                mainMenu.update(this, delta);
                mainMenu.setMenuPage(MainMenu.KEY_GAME_OVER);
                break;
            case QUIT:
                System.exit(0);
                break;
        }

    }

    public void draw(Graphics graphics) {

        starFieldEffect.draw(graphics);

        switch (gameState.state) {
            case MENU:
            case GAMEOVER:
                mainMenu.draw(graphics);
                break;
            case PLAY:
                currentGameLogic.draw(graphics);
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

    public void startAndPlay(float targetFps, GameRender graphics) {

        final float timePerFrame = 1000000000.0f / targetFps;

        long previousTime = System.nanoTime();

        float delta = 0.0f;

        while (gameState.isGameRunning()) {

            long currentTime = System.nanoTime();

            delta += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (delta >= 1) {
                update(delta / targetFps);
                graphics.repaint();
                delta--;
            }
        }
    }
}
