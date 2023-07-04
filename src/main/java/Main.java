import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Config cfg = new Config();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.BLACK);
        frame.setUndecorated(true);
        frame.add(new MenuComponent(cfg));
        frame.setVisible(true);
    }
}
