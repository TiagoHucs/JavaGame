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

    public GameWindow(GameComponent game) {

        this.setUndecorated(true);
        this.setIgnoreRepaint(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);

        this.config = new Config(getGameSize());

        if (Config.isDebugMode()) {
            this.setSize(config.getGameResolution());
            this.setPreferredSize(config.getGameResolution());
        } else {
            this.setSize(config.getScreenResolution());
            this.setPreferredSize(config.getScreenResolution());
            this.setFullScreen();
        }

        this.game = game;
        this.game.setConfig(config);
        this.game.init();

        this.addKeyListener(this.game);

        this.render = new GameRender(this);
        this.render.setVisible(true);
        this.render.setFocusable(false);

        this.add(render);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
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

    @Override
    public void paint(Graphics g) {
        game.draw(g);
    }

    public void play() {
        game.startAndPlay(FPS, render);
    }
}
