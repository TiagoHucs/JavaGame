import game.GameComponent;
import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Main {

    public static boolean DEBUG_MODE = true;

    public static void main(String[] args) {
        Config cfg = new Config();
        JFrame frame = new JFrame("Java Shooter Game 2D");
        defaultSetup(frame, cfg, new GameComponent(cfg));
    }

    public static void defaultSetup(JFrame frame, Config cfg, GameComponent component) {

        component.setPreferredSize(new Dimension(cfg.getLarguraTela(), cfg.getAlturaTela()));
        component.requestFocusInWindow();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(component);
        frame.addKeyListener(component);

        frame.setUndecorated(!DEBUG_MODE);
        frame.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
        frame.setResizable(DEBUG_MODE);

        frame.setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);

        if (!DEBUG_MODE) {
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();
            graphicsDevice.setFullScreenWindow(frame);
        }

        frame.setVisible(true);
    }
}
