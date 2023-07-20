package partition;

import engine.GameWindow;
import entities.GameObject;
import game.GameComponent;
import game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class QuadtreeDemo extends GameComponent {

    public class Particle extends GameObject {
        public boolean collided;
        @Override
        public void move(float delta) {
            getPosition().x += randRange();
            getPosition().y += randRange();
        }
        public void draw(Graphics g) {

            if (collided) g.setColor(Color.RED);
            else g.setColor(Color.BLUE);

            g.fillRect((int) getPosition().x, (int) getPosition().y, (int) getSize().y, (int) getSize().y);
        }
    }

    private Quadtree quadtree;
    private Set<Particle> particles = new LinkedHashSet<>();
    private Set<GameObject> selectedParticles = new LinkedHashSet<>();
    private Rectangle mouseBounds = new Rectangle(0, 0, 256, 256);

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        int w = getCfg().getGameWidth();
        int h = getCfg().getGameHeight();

        Rectangle screenBounds = new Rectangle(0, 0, w, h);
        this.quadtree = new Quadtree(screenBounds, 4);
    }

    @Override
    public void update(float delta) {

        // 1 - Movimento todo mundo
        particles.forEach(p -> {
            p.move(delta);
            quadtree.update(p);
        });

        // 2 - Busco por colisoes
        particles.forEach(p -> {
            p.collided = quadtree.query(p, new LinkedHashSet<>());
        });

    }

    private float randRange() {
        return (float) ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
    }

    private Point2D.Float randSize() {
        float w = (float) ThreadLocalRandom.current().nextDouble(8, 64);
        float h = (float) ThreadLocalRandom.current().nextDouble(8, 64);
        return new Point2D.Float(w, h);
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        quadtree.draw(g);

        particles.forEach(p -> {
            p.draw(g);
        });

        highlightSelectedParticles(g);
    }

    private void highlightSelectedParticles(Graphics g) {

        if (selectedParticles.isEmpty())
            return;

        g.setColor(Color.GREEN);
        g.drawRect(mouseBounds.x, mouseBounds.y, mouseBounds.width, mouseBounds.height);

        selectedParticles.forEach(gameObject -> {
            g.drawRect(
                    gameObject.getPositionWithOffsetX(),
                    gameObject.getPositionWithOffsetY(),
                    (int) gameObject.getSize().y,
                    (int) gameObject.getSize().y);
        });

        g.drawString("Objetos encontrados: " + selectedParticles.size(), 2, g.getFont().getSize());
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Point position = e.getPoint();

        Particle object = createGameObject(position);
        quadtree.insert(object);
        particles.add(object);

        findGameObjectsInMouseBounds(position);
    }

    private void findGameObjectsInMouseBounds(Point position) {

        // Limpa consulta anterior
        selectedParticles.clear();

        // Atualiza area de pesquisa para posicao atual do mouse
        mouseBounds.x = position.x - (mouseBounds.width / 2);
        mouseBounds.y = position.y - (mouseBounds.height / 2);

        // Faz a pesquisa
        quadtree.query(mouseBounds, selectedParticles);
    }

    private Particle createGameObject(Point position) {
        Particle object = new Particle();
        object.setPosition(new Point2D.Float(position.x, position.y));
        object.setSize(randSize());
        return object;
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        new GameWindow(Toolkit.getDefaultToolkit().getScreenSize(), new QuadtreeDemo()).play();
    }
}
