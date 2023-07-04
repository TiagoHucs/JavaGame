import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JComponent implements KeyListener {

    private Config cfg = new Config();
    private int selectedOption;
    private final String[] options;
    private JFrame gameFrame;

    public Main(String[] options) {
        this.options = options;
        this.selectedOption = 0;
        setPreferredSize(new Dimension(cfg.getLarguraTela(), cfg.getAlturaTela()));
        setFocusable(true);
        addKeyListener(this);
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
            g.drawString(options[i], 10, 20 + i * 20);
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
            gameFrame = new JFrame();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setBackground(Color.BLACK);
            gameFrame.setUndecorated(true);
            gameFrame.add(new Game());
            gameFrame.setVisible(true);
        }

        //TELA DE EXEMPLO
        if (selectedOption == 1) {
            JFrame configuracoes = new JFrame("Configurações");
            configuracoes.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
            configuracoes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            configuracoes.setVisible(true);
        }
        //TELA DE EXEMPLO
        if (selectedOption == 2) {
            JFrame creditos = new JFrame("Créditos");
            creditos.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
            creditos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            creditos.setVisible(true);
        }

        // Fechar a janela do menu
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String[] options = {"JOGAR", "CONFIGURAÇÕES","CRÉDITOS"};
                JFrame frame = new JFrame("Menu");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 200);
                frame.setLocationRelativeTo(null);

                Main menuComponent = new Main(options);
                frame.add(menuComponent);

                frame.setVisible(true);
            }
        });
    }
}
