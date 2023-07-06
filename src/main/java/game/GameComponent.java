package game;

import effects.StarFieldEffect;
import entities.Enemy;
import entities.Ship;
import entities.Shot;
import utilities.Config;
import utilities.ResourceManager;

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

    private PauseMenu pauseMenu;
    private StarFieldEffect starFieldEffect;
    private Ship nave = new Ship();
    private ArrayList<Shot> listaTiros = new ArrayList<Shot>();
    private ArrayList<Enemy> listaInimigos = new ArrayList<Enemy>();
    private Random r = new Random();
    private Colisor colisor = new Colisor();
    private boolean paused = true;
    private int level = 1;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    public GameComponent(Config cfg) {

        // Pre-Load dos audios
        ResourceManager.get().loadResources(
                "/audio/bling.wav",
                "/audio/changing-tab.wav",
                "/audio/fighters-coming.wav",
                "/audio/im-hit.wav",
                "/audio/level-music.wav",
                "/audio/tap.wav");

        this.cfg = cfg;
        this.pauseMenu = new PauseMenu(cfg);
        addKeyListener(this);
        setDoubleBuffered(true);
        setFocusable(true);
        geraInimigos();
        starFieldEffect = new StarFieldEffect(cfg.getLarguraTela(), cfg.getAlturaTela(), 400);
        animationThread = new Thread(this);
        animationThread.start();
        SoundManager.get().playMusic("/audio/typical-trap-loop_2.wav");
    }

    public void paintGame(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, cfg.getLarguraTela(), cfg.getAlturaTela());

        starFieldEffect.draw(g);

        g.setColor(Color.gray);
        g.drawImage(nave.getImage(),
                nave.getX() + nave.getOffSetX(),
                nave.getY() + nave.getOffSetY(),this);

        for (Enemy i : listaInimigos) {
            g.drawImage(i.getImage(),
                    i.getX() + i.getOffSetX(),
                    i.getY() + i.getOffSetY(),this);
        }

        g.setColor(Color.RED);
        for (Shot tiro : listaTiros) {
            g.fillRect(tiro.getX() + tiro.getOffSetX(),tiro.getY() + tiro.getOffSetY(), tiro.getLargura(), tiro.getAltura());
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
                SoundManager.get().playSound("/audio/im-hit.wav");
            }

        }

        listaInimigos.removeAll(listaInimigosDestruidos);
        listaTiros.removeAll(listaTirosDestruidos);

        SoundManager.get().checkSounds(cfg.isMuted() ? 0.0f : 1.0f);
    }

    public void paintComponent(Graphics g) {
        paintGame(g);
        if(paused){
            pauseMenu.paintMenu(g);
        }
        g.dispose();
        Toolkit.getDefaultToolkit().sync();
    }


    private void geraInimigos() {

        for (int i = 0; i < 10; i++) {
            listaInimigos.add(new Enemy(r.nextInt(cfg.getResolution()), i * -50));
        }

        SoundManager.get().playSound("/audio/fighters-coming.wav");
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
            SoundManager.get().playSound("/audio/changing-tab.wav");
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(!this.paused){
                SoundManager.get().playSound("/audio/bling.wav");
                listaTiros.add(nave.atirar());
            }
        }

        //CONTROLE VIA MENU
        if(paused){
            if(PauseMenu.OPT_RETOMAR_PARTIDA.equals(pauseMenu.control(e))){
                paused = false;
            }
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
