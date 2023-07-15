package pong;

import game.GameComponent;
import utilities.Config;

import javax.swing.*;
import java.awt.*;

public class Pong {
    public static void main(String[] args) {

        final boolean debugMode = isDebugMode();

        Config cfg = new Config(Toolkit.getDefaultToolkit().getScreenSize(), debugMode);

        GameComponent game = new PongGameComponent(cfg);

        JFrame gameWindow = new JFrame("PONG");
        cfg.setup(gameWindow);

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.add(game);
        gameWindow.setResizable(false);
        gameWindow.setUndecorated(true);
        gameWindow.setIgnoreRepaint(true);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.addKeyListener(game);

        if (!debugMode) {

            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();
            graphicsDevice.setFullScreenWindow(gameWindow);

            // Melhora velocidade
            gameWindow.createBufferStrategy(2);
        }

        gameWindow.setVisible(true);
        gameWindow.requestFocusInWindow();
    }

    public static boolean isDebugMode() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
    }
}
