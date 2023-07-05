import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuComponent extends JComponent implements KeyListener {

    private Config cfg;
    private int selectedOption;
    private final String[] options = {"JOGAR", "CONFIGURAÇÕES","CRÉDITOS"};

    public MenuComponent(Config cfg) {
        this.cfg = cfg;
        addKeyListener(this);
        setFocusable(true);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
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

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            selectedOption = Math.max(0, selectedOption - 1);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            selectedOption = Math.min(options.length - 1, selectedOption + 1);
        } else if (keyCode == KeyEvent.VK_ENTER) {
            openSelectedOption();
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            JFrame menuFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            menuFrame.dispose();
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    private void openSelectedOption() {
        String selected = options[selectedOption];

        if (selectedOption == 0) {
            JFrame gameFrame = new JFrame();
            Main.defaultSetup(gameFrame, cfg);
            gameFrame.add(new GameComponent(cfg));
            gameFrame.setVisible(true);
        }

        //TELA DE EXEMPLO
        if (selectedOption == 1) {
            JFrame frame = new JFrame();
            Main.defaultSetup(frame, cfg);
            frame.add(new ConfigComponent(cfg));
            frame.setVisible(true);
        }
        //TELA DE EXEMPLO
        if (selectedOption == 2) {
            JFrame frame = new JFrame();
            Main.defaultSetup(frame, cfg);
            frame.add(new CreditsComponent(cfg));
            frame.setVisible(true);
        }

        // Fechar a janela do menu
        SwingUtilities.getWindowAncestor(this).dispose();
    }

}
