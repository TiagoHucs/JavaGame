package game;

import effects.StarFieldEffect;
import engine.GameRender;
import menu.AbstractMenu;
import utilities.Config;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class GameComponent implements KeyListener, MouseListener {
    private Config cfg;
    protected SoundManager soundManager;
    protected StarFieldEffect starFieldEffect;
    public GameState gameState;
    public GameLogic currentGameLogic;
    protected AbstractMenu menu;

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
        this.starFieldEffect = new StarFieldEffect(this, true);
    }

    public void update(float delta) {

        switch (gameState.state) {
            case MENU:
                menu.update(this, delta);
                break;
            case PLAY:
                currentGameLogic.update(this, delta);
                break;
            case GAMEOVER:
                menu.update(this, delta);
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
                menu.draw(graphics);
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
                menu.keyPressed(e);
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
                menu.keyReleased(e);
                break;
        }

    }

    public void startAndPlay(float targetFps, GameRender graphics) {

        final float timePerFrame = 1000000000F / targetFps;
        final float fixedDeltaTime = 1.0F / targetFps;

        long previousTime = System.nanoTime();

        float delta = 0F;

        while (gameState.isGameRunning()) {

            long currentTime = System.nanoTime();

            delta += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (delta >= 1F) {

                if (graphics.isVsync()) {
                    update(fixedDeltaTime);

                } else {
                    update(delta / targetFps);
                }

                graphics.render();
                delta--;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     * @param e the event to be processed
     */
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     * @param e the event to be processed
     */
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     * @param e the event to be processed
     */
    public void mouseExited(MouseEvent e) {

    }
}
