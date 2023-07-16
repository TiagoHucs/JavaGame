package engine;

import game.GameComponent;
import utilities.Config;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final Config config;
    private volatile float delta = 0.0f;
    private GameComponent game;

    public GameWindow(GameComponent game) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setIgnoreRepaint(true);
        this.setBackground(Color.BLACK);

        this.config = new Config(getGameSize());
        game.setConfig(config);
        game.init();
        this.addKeyListener(game);
        this.game = game;

        if (Config.isDebugMode()) {
            this.setSize(config.getGameResolution());
            this.setPreferredSize(config.getGameResolution());
        } else {
            this.setSize(config.getScreenResolution());
            this.setPreferredSize(config.getScreenResolution());
            this.setFullScreen();
        }

        this.add(new GameRender(this, game));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.requestFocusInWindow();
    }

    public void setFullScreen() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);
    }

    public Dimension getGameSize() {

        // Numeros mágicos baseados no tamanho da imagem do jogador
        Dimension playerSize = new Dimension(48, 58);

        // Quantidade de tiles por eixo
        Dimension tileCount = new Dimension(20, 10);

        return new Dimension(playerSize.width * tileCount.width, playerSize.height * tileCount.height);
    }

    public void play() {

        final float timePerFrame = 1000000000.0f / GameRender.FPS;

        long previousTime = System.nanoTime();

        while (true) {

            long currentTime = System.nanoTime();

            delta += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (delta >= 1) {
                game.update(delta / GameRender.FPS);
                repaint();
                delta--;
            }
        }
    }
}
