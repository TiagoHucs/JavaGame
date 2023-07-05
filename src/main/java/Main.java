import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Main {

    public static void main(String[] args) {
        Config cfg = new Config();
        JFrame frame = new JFrame("Java Shooter Game 2D");
        defaultSetup(frame, cfg, new MenuComponent(cfg));
    }

    public static void defaultSetup(JFrame frame, Config cfg, JComponent component) {

        component.setPreferredSize(new Dimension(cfg.getLarguraTela(), cfg.getAlturaTela()));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(component);
        frame.addKeyListener((KeyListener) component);

        frame.setUndecorated(true);
        frame.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
        frame.setResizable(false);
        // frame.pack();

        frame.setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(frame);

        frame.setVisible(true);
    }
}
