import game.GameComponent;
import utilities.Config;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static boolean DEBUG_MODE = true;
    public static void main(String[] args) {

        Config cfg = new Config();

        GameComponent game = new GameComponent(cfg);

        JFrame gameWindow = new JFrame("Java Shooter Game 2D");
        cfg.setup(gameWindow);

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.add(game);
        gameWindow.setResizable(false);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.addKeyListener(game);

        if (!DEBUG_MODE) {
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();
            graphicsDevice.setFullScreenWindow(gameWindow);
        }

        gameWindow.setVisible(true);
    }
}
