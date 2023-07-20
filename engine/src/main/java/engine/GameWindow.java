package engine;

import game.GameComponent;
import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameWindow extends JFrame {
    public static final float FPS = 60.0f;
    private final Config config;
    private GameComponent game;
    private GameRender render;
    private Canvas canvas;

    private Dimension viewport;

    public GameWindow(Dimension viewport, GameComponent game) {
        this.setUndecorated(true);
        this.setIgnoreRepaint(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);

        this.config = new Config(viewport);

        if (Config.isDebugMode()) {

            canvas = new Canvas();
            canvas.setSize(config.getGameResolution());
            canvas.setPreferredSize(config.getGameResolution());
            canvas.setIgnoreRepaint(true);

            this.setSize(config.getGameResolution());
            this.setPreferredSize(config.getGameResolution());
            this.add(canvas);
            this.pack();

            canvas.createBufferStrategy(2);

        } else {
            this.setSize(config.getScreenResolution());
            this.setPreferredSize(config.getScreenResolution());
            this.setFullScreen();
            this.createBufferStrategy(2);
        }

        this.game = game;
        this.game.setConfig(config);
        this.game.init();

        if (canvas == null) {
            this.addKeyListener(this.game);
            this.addMouseListener(this.game);
            this.requestFocusInWindow();

        } else {
            canvas.addKeyListener(this.game);
            canvas.addMouseListener(this.game);
            canvas.requestFocusInWindow();
        }

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.render = new GameRender(this, viewport);
    }

    public void setFullScreen() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);
    }

    public void play() {
        game.startAndPlay(FPS, render);
    }

    @Override
    public void paint(Graphics graphics) {
        // Só para ficar mais fácil na classe GameWindow, pois estou renderizando frame a frame manualmente
        game.draw(graphics);
    }

    @Override
    public BufferStrategy getBufferStrategy() {

        // Modo Exclusivo para FullScreen
        if (canvas == null) {
            return super.getBufferStrategy();
        }

        // Modo Janela
        return canvas.getBufferStrategy();
    }

    public Config getConfig() {
        return config;
    }

}
