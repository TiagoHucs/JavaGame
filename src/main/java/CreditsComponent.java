import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CreditsComponent extends JComponent implements KeyListener {

    private Config cfg;
    private final String[] creditos = {"TIAGOHUCS", "MACARRAUM", "MURINGA"};

    public CreditsComponent(Config cfg) {
        this.cfg = cfg;
        addKeyListener(this);
        setFocusable(true);
        repaint();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, cfg.getLarguraTela(), cfg.getAlturaTela());

        for (int i = 0; i < creditos.length; i++) {
            g.setColor(Color.YELLOW);
            g.drawString(creditos[i], cfg.getLarguraTela()/3, (cfg.getAlturaTela()/3) + i * 20);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            JFrame menuFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            menuFrame.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
