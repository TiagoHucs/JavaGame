package game;

import effects.StarFieldEffect;
import entities.Enemy;
import entities.Ship;
import entities.Shot;
import utilities.Config;

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
    private final Config cfg;

    private final PauseMenu pauseMenu;
    private final StarFieldEffect starFieldEffect;
    private final Ship nave = new Ship();
    private final ArrayList<Shot> listaTiros = new ArrayList<Shot>();
    private final ArrayList<Enemy> listaInimigos = new ArrayList<Enemy>();
    private final Random r = new Random();
    private final Colisor colisor = new Colisor();
    private boolean paused = true;
    private int level = 1;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private final SoundManager soundManager;

    public GameComponent(Config cfg) {

        this.cfg = cfg;
        this.soundManager = new SoundManager();
        this.soundManager.loadSounds("/audio");

        this.pauseMenu = new PauseMenu(this);

        addKeyListener(this);
        setDoubleBuffered(true);
        setFocusable(true);

        starFieldEffect = new StarFieldEffect(cfg.getLarguraTela(), cfg.getAlturaTela(), 400);

        geraInimigos();

        animationThread = new Thread(this);
        animationThread.start();

        soundManager.playMusic("typical-trap-loop_2.wav");
    }

    public final SoundManager getSoundManager() {
        return soundManager;
    }

    public final Config getCfg() {
        return cfg;
    }

    public void paintGame(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, cfg.getLarguraTela(), cfg.getAlturaTela());

        starFieldEffect.draw(g);

        g.setColor(Color.gray);
        g.drawImage(nave.getImage(),
                nave.getX() + nave.getOffSetX(),
                nave.getY() + nave.getOffSetY(), this);

        for (Enemy i : listaInimigos) {
            g.drawImage(i.getImage(),
                    i.getX() + i.getOffSetX(),
                    i.getY() + i.getOffSetY(), this);
        }

        g.setColor(Color.RED);
        for (Shot tiro : listaTiros) {
            g.fillRect(tiro.getX() + tiro.getOffSetX(), tiro.getY() + tiro.getOffSetY(), tiro.getLargura(), tiro.getAltura());
        }

        g.setColor(Color.YELLOW);
        g.drawString("Level: " + level, 10, 20);
        g.drawString("Energy: " + nave.getEnergia(), 10, 40);
        g.drawString("Enemies: " + listaInimigos.size(), 10, 60);
        g.drawString("Paused: " + paused, 10, 80);
        g.drawString("Muted: " + cfg.isMuted(), 10, 100);
    }

    private void update() {

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
                soundManager.playSound("im-hit.wav");
            }
        }

        listaInimigos.removeAll(listaInimigosDestruidos);
        listaTiros.removeAll(listaTirosDestruidos);
    }

    public void paintComponent(Graphics g) {
        paintGame(g);
        if (paused) {
            pauseMenu.paintMenu(g);
        }
        g.dispose();
        Toolkit.getDefaultToolkit().sync();
    }


    private void geraInimigos() {

        for (int i = 0; i < 10; i++) {
            listaInimigos.add(new Enemy(r.nextInt(cfg.getResolution()), i * -50));
        }

        soundManager.playSound("fighters-coming.wav");
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

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            this.paused = !this.paused;
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            cfg.setMuted(!cfg.isMuted());
            soundManager.toogleMute(cfg.isMuted());
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
            this.paused = !this.paused;
            soundManager.playSound("changing-tab.wav");
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!this.paused) {
                soundManager.playSound("bling.wav");
                listaTiros.add(nave.atirar());
            }
        }

        //CONTROLE VIA MENU
        if (paused && PauseMenu.OPT_RETOMAR_PARTIDA.equals(pauseMenu.control(e))) {
            paused = false;
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
                    update();
                }
                repaint();
                delta--;
            }

        }
    }
}
