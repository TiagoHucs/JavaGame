import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Config cfg = new Config();
        JFrame frame = new JFrame();
        defaultSetup(frame, cfg);
        frame.add(new MenuComponent(cfg));
        frame.setVisible(true);
    }

    public static void defaultSetup(JFrame frame, Config cfg) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
        frame.setBackground(Color.BLACK);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = environment.getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(frame);
    }
}
