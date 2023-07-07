package game;

import effects.StarFieldEffect;
import entities.Enemy;
import entities.Shot;
import menu.PauseMenu;
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
    private final PauseMenu newPauseMenu;
    private final StarFieldEffect starFieldEffect;
    private final ArrayList<Enemy> listaInimigos = new ArrayList<Enemy>();
    private final Random r = new Random();
    private final Colisor colisor = new Colisor();
    private final Font font;
    private boolean paused = true;
    private final SoundManager soundManager;
    private Set<PlayerState> players = new HashSet<PlayerState>(4);

    public GameComponent(Config cfg) {

        this.font = new Font("TimesRoman", Font.PLAIN, 25);
        this.cfg = cfg;
        this.soundManager = new SoundManager();
        this.soundManager.loadSounds("/audio");

        this.newPauseMenu = new PauseMenu(this);

        addKeyListener(this);
        setDoubleBuffered(true);
        setFocusable(true);

        starFieldEffect = new StarFieldEffect(cfg.getLarguraTela(), cfg.getAlturaTela(), 400);

        geraInimigos();

        // Jogador 1
        PlayerState p1 = new PlayerState(players.size());
        p1.getShip().setX(p1.getShip().getX() - 40);
        players.add(p1);

        // Jogador 2
        PlayerState p2 = new PlayerState(players.size());
        p2.getShip().setY(p2.getShip().getY() - 20);
        players.add(p2);

        // Jogador 3
        PlayerState p3 = new PlayerState(players.size());
        p3.getShip().setX(p3.getShip().getX() + 40);
        players.add(p3);

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

        for (Enemy i : listaInimigos) {
            g.drawImage(i.getImage(),
                    i.getX() + i.getOffSetX(),
                    i.getY() + i.getOffSetY(), this);
        }

        for (PlayerState playerState : players) {
            playerState.draw(g, this);
        }
    }

    private void update() {

        Set<Enemy> listaInimigosDestruidos = new HashSet<Enemy>();

        //se acabarem os inimigos gere mais
        if (listaInimigos.size() < 1) {
            geraInimigos();
        }

        for (Enemy inimigo : listaInimigos) {
            inimigo.move();
            if (inimigo.getY() > cfg.getAlturaTela()) {
                inimigo.setY(-100);
                inimigo.setX(r.nextInt(cfg.getResolution()));
            }
        }

        for (PlayerState playerState : players) {
            playerState.update(this);
        }

        for (Enemy inimigo : listaInimigos) {

            for (PlayerState playerState : players) {

                Set<Shot> listaTirosDestruidos = new HashSet<Shot>();

                for (Shot tiro : playerState.getBullets()) {
                    if (tiro.getY() < -10) {
                        listaTirosDestruidos.add(tiro);

                    } else if (colisor.detectaColisao(tiro, inimigo)) {
                        listaTirosDestruidos.add(tiro);

                        if (inimigo.getLifes() == 0){
                            listaInimigosDestruidos.add(inimigo);
                        } else {
                            inimigo.setLifes(inimigo.getLifes()-1);
                        }
                        playerState.addScore(100);
                    }
                }

                if (colisor.detectaColisao(playerState.getShip(), inimigo)) {
                    listaInimigosDestruidos.add(inimigo);
                    playerState.getShip().sofreDano(25);
                    soundManager.playSound("im-hit.wav");
                }

                playerState.getBullets().removeAll(listaTirosDestruidos);
            }
        }

        listaInimigos.removeAll(listaInimigosDestruidos);
    }

    public void paintComponent(Graphics g) {
        paintGame(g);
        if (paused) {
            newPauseMenu.paintMenu(g);
        }
        g.dispose();
        Toolkit.getDefaultToolkit().sync();
    }

    private void geraInimigos() {

        for (int i = 0; i < 20; i++) {
            listaInimigos.add(new Enemy(r.nextInt(cfg.getResolution()), i * -50));
        }

        soundManager.playSound("fighters-coming.wav");
    }

    public void pause(){
        paused = !paused;
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
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            soundManager.playSound("changing-tab.wav");
            this.pause();
        }
        for (PlayerState playerState : players) {
            playerState.getActions().keyPressed(e);
        }
        //CONTROLE VIA MENU
        if (paused) {
            newPauseMenu.control(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for (PlayerState playerState : players) {
            playerState.getActions().keyReleased(e);
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
