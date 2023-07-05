import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    public static void main(String[] args) {
        Config cfg = new Config();
        JFrame frame = new JFrame();
        defaultSetup(frame, cfg);
        GameComponent game = new GameComponent(cfg);
        frame.add(game);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                game.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                game.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game.keyReleased(e);
            }
        });
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
