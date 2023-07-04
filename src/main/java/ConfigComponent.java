import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConfigComponent extends JComponent implements KeyListener {

    private Config cfg;

    private boolean muted = true;
    private final String[] options = {"SOUND", "RESOLUTION","CONTROLERS"};
    private int selectedOption = 0;
    private int level = 1;

    public ConfigComponent(Config cfg) {
        this.cfg = cfg;
        addKeyListener(this);
        setFocusable(true);

        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                //TODO: vai para keys
                    repaint();

            }

        });


    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0,0,cfg.getLarguraTela(),cfg.getAlturaTela());

        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], cfg.getLarguraTela()/3, (cfg.getAlturaTela()/3) + i * 20);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            selectedOption = Math.max(0, selectedOption - 1);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            selectedOption = Math.min(options.length - 1, selectedOption + 1);
        } else if (keyCode == KeyEvent.VK_ENTER) {
            //openSelectedOption();
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            JFrame menuFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            menuFrame.dispose();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
