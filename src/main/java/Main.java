import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JComponent implements KeyListener {

    Config cfg = new Config();
    private int selectedOption;
    private final String[] options;

    public Main(String[] options) {
        this.options = options;
        this.selectedOption = 0;
        setPreferredSize(new Dimension(200, 100));
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
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    private void openSelectedOption() {
        String selected = options[selectedOption];
        System.out.println("Selected option: " + selected);

        if (selectedOption == 0) {
            GameScreen game = new GameScreen();
            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            game.setVisible(true);
        }

        // Por exemplo, creditos, cofiguracoes
        if (selectedOption == 1) {
            JFrame tela2 = new JFrame("Tela 2");
            tela2.setSize(300, 200);
            tela2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tela2.setVisible(true);
        }

        // Fechar a janela do menu
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String[] options = {"Jogar", "Configurações","Creditos"};
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
