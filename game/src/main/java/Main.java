import game.GameComponent;
import utilities.Config;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Config cfg = new Config(Toolkit.getDefaultToolkit().getScreenSize());

        GameComponent game = new GameComponent(cfg);

        JFrame gameWindow = new JFrame("Java Shooter Game 2D");
        cfg.setup(gameWindow);

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.add(game);
        gameWindow.setResizable(false);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.addKeyListener(game);

        if (!isDebugMode()) {
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();
            graphicsDevice.setFullScreenWindow(gameWindow);
        }

        gameWindow.setVisible(true);
    }

    public static boolean isDebugMode() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
    }
}
