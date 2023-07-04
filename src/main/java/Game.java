import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Game extends JComponent implements KeyListener {

    Config cfg = new Config();

    private Ship nave = new Ship();
    private ArrayList<Shot> listaTiros = new ArrayList<Shot>();
    private ArrayList<Enemy> listaInimigos = new ArrayList<Enemy>();
    private int speedDefault = cfg.getResolution() / 200;
    private Random r = new Random();
    private Colisor colisor = new Colisor();
    private boolean paused = false;
    private int level = 1;

    public Game() {
        addKeyListener(this);
        setFocusable(true);

        geraInimigos();

        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (!paused) {
                        loop();
                    }
                    repaint();
                    try {
                        Thread.sleep(16);
                    } catch (Exception ex) {
                    }
                }
            }

        });

        animationThread.start();

    }

    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, cfg.getLarguraTela(), cfg.getAlturaTela());

        g.setColor(Color.gray);
        g.drawImage(nave.getImage(), nave.getX(), nave.getY(), nave.getLargura(), nave.getAltura(), this);

        for (Enemy i : listaInimigos) {
            g.drawImage(i.getImage(), i.getX(), i.getY(), i.getLargura(), i.getLargura(), this);
        }

        g.setColor(Color.RED);
        for (Shot tiro : listaTiros) {
            g.fillRect(tiro.getX(), tiro.getY(), tiro.getLargura(), tiro.getAltura());
        }

        g.setColor(Color.YELLOW);
        g.drawString("Level: " + level, 10, 20);
        g.drawString("Energia: " + nave.getEnergia(), 10, 40);
        g.drawString("Inimigos: " + listaInimigos.size(), 10, 60);
        g.drawString("Pausado: " + paused, 10, 80);

    }

    private void geraInimigos() {
        for (int i = 0; i < 10; i++) {
            listaInimigos.add(new Enemy(r.nextInt(cfg.getResolution()), i * -50));
        }
        nave.playSound("fighters-coming.wav");
    }

    private void loop() {

        nave.move();

        //se acabarem os inimigos gere mais
        if (listaInimigos.size() < 1) {
            geraInimigos();
            level++;
        }

        for (Shot tiro : listaTiros) {
            tiro.move();
        }

        for (Enemy inimigo : listaInimigos) {
            inimigo.move();
            if (inimigo.getY() > cfg.getAlturaTela()) {
                inimigo.setY(-100);
                inimigo.setX(r.nextInt(cfg.getResolution()));
            }
        }

        Set<Enemy> listaInimigosDestruidos = new HashSet<Enemy>();
        Set<Shot> listaTirosDestruidos = new HashSet<Shot>();

        for (Enemy inimigo : listaInimigos) {
            for (Shot tiro : listaTiros) {
                if (tiro.getY() < -10) {
                    listaTirosDestruidos.add(tiro);
                } else if (colisor.detectaColisao(tiro, inimigo)) {
                    listaTirosDestruidos.add(tiro);
                    listaInimigosDestruidos.add(inimigo);
                }
            }

            if (colisor.detectaColisao(nave, inimigo)) {
                listaInimigosDestruidos.add(inimigo);
                nave.sofreDano(25);
            }

        }

        listaInimigos.removeAll(listaInimigosDestruidos);
        listaTiros.removeAll(listaTirosDestruidos);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {
            this.paused = !this.paused;
        }

        if (!paused) {

            if (e.getKeyCode() == KeyEvent.VK_UP) {
                nave.setVelocidadeY(-speedDefault);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                nave.setVelocidadeY(speedDefault);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                nave.setVelocidadeX(-speedDefault);
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                nave.setVelocidadeX(speedDefault);
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                listaTiros.add(nave.atirar());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            nave.setVelocidadeY(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            nave.setVelocidadeY(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            nave.setVelocidadeX(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            nave.setVelocidadeX(0);
        }

    }

}
