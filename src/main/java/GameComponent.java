import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameComponent extends JComponent implements KeyListener, Runnable {
    private final Thread animationThread;
    private Config cfg;

    private Ship nave = new Ship();
    private ArrayList<Shot> listaTiros = new ArrayList<Shot>();
    private ArrayList<Enemy> listaInimigos = new ArrayList<Enemy>();
    private Random r = new Random();
    private Colisor colisor = new Colisor();
    private boolean paused = false;
    private boolean muted = true;
    private int level = 1;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    public GameComponent(Config cfg) {
        this.cfg = cfg;
        addKeyListener(this);
        setDoubleBuffered(true);
        setFocusable(true);
        geraInimigos();
        animationThread = new Thread(this);
        animationThread.start();
    }

    public void paintComponent(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, cfg.getLarguraTela(), cfg.getAlturaTela());

        g.setColor(Color.gray);
        g.drawImage(nave.getImage(), nave.getX(), nave.getY(),this);

        for (Enemy i : listaInimigos) {
            g.drawImage(i.getImage(), i.getX(), i.getY(),this);
        }

        g.setColor(Color.RED);
        for (Shot tiro : listaTiros) {
            g.fillRect(tiro.getX(), tiro.getY(), tiro.getLargura(), tiro.getAltura());
        }

        g.setColor(Color.YELLOW);
        g.drawString("Level: " + level, 10, 20);
        g.drawString("Energy: " + nave.getEnergia(), 10, 40);
        g.drawString("Enemies: " + listaInimigos.size(), 10, 60);
        g.drawString("Paused: " + paused, 10, 80);
        g.drawString("Muted: " + muted, 10, 100);

        g.dispose();
    }

    private void geraInimigos() {

        for (int i = 0; i < 10; i++) {
            listaInimigos.add(new Enemy(r.nextInt(cfg.getResolution()), i * -50));
        }

        playSound("fighters-coming.wav");
    }

    private void loop() {

        moveShip();

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
                playSound("im-hit.wav");
            }

        }

        listaInimigos.removeAll(listaInimigosDestruidos);
        listaTiros.removeAll(listaTirosDestruidos);
    }

    private void moveShip() {
        //Horizontalmente
        if (left == right) {
            nave.slowDownX();
        } else if (left) {
            nave.decreaseXVelocity();
        } else if (right) {
            nave.increaseXVelocity();
        }
        //Verticalmente
        if (up == down) {
            nave.slowDownY();
        } else if (up) {
            nave.decreaseYVelocity();
        } else if (down) {
            nave.increaseYVelocity();
        }
        nave.move();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private void playSound(String filename) {
        if (!muted) {
            try {
                Clip sound = ResourceManager.get().getAudio(filename);
                sound.start();
            } catch (Exception ex) {
                System.err.println("Erro ao tocar o som = " + filename);
                ex.printStackTrace();
            }
        }
    }

    private void playMusic(String filename) {
        if (!muted) {
            try {
                Clip sound = ResourceManager.get().getAudio(filename);
                sound.start();
                sound.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception ex) {
                System.err.println("Erro ao tocar a música = " + filename);
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {
            this.paused = !this.paused;
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            this.muted = !this.muted;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            playSound("bling.wav");
            listaTiros.add(nave.atirar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / 60;
        double delta = 0.0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (animationThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                if (!paused) {
                    loop();
                }
                repaint();
                delta--;
            }

        }
    }
}
