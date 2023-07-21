package partition;

import engine.GameWindow;
import entities.GameObject;
import game.GameComponent;
import game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class QuadtreeDemo extends GameComponent {

    public class Particle extends GameObject {
        public boolean collided;

        @Override
        public void move(float delta) {
            getPosition().x += randRange();
            getPosition().y += randRange();
            this.collided = false;
        }

        public void draw(Graphics g) {

            if (collided) g.setColor(Color.RED);
            else g.setColor(Color.BLUE);

            drawBordered(g, Color.WHITE, this);
        }
    }

    private Rectangle mouseBounds;
    private Quadtree<Particle> quadtree;
    private List<QuadTreeItem<Particle>> particles = new CopyOnWriteArrayList<>();
    private List<QuadTreeItem<Particle>> selectedParticles = new CopyOnWriteArrayList<>();

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        int brushSize = 16;
        int w = getCfg().getGameWidth();
        int h = getCfg().getGameHeight();

        this.quadtree = new Quadtree<Particle>(new Rectangle(0, 0, w, h), brushSize / 2);

        this.mouseBounds = new Rectangle(
                (w / 2) - (brushSize / 2),
                (h / 2) - (brushSize / 2),
                brushSize, brushSize);

        for (int i = 0; i < w + h / brushSize; i++) {
            int x = (int) (Math.random() * w);
            int y = (int) (Math.random() * h);
            particles.add(createGameObject(new Point(x, y)));
        }
    }

    public void update(QuadTreeItem<Particle> p, float delta) {
        p.item.move(delta);
        p.item.limitToScreenBounds(this);
        p.bounds = p.item.getBounds();
    }

    public void handleCollisions(QuadTreeItem<Particle> p) {

        List<QuadTreeItem<Particle>> result = quadtree.search(p);

        for (QuadTreeItem<Particle> other : result) {
            p.item.collided = true;
            other.item.collided = true;
        }

    }

    @Override
    public void update(float delta) {

        for (QuadTreeItem<Particle> p : particles) {
            update(p, delta);
            quadtree.realocate(p);
            handleCollisions(p);
        }

        findGameObjectsInMouseBounds();
    }

    private float randRange() {
        return (float) ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
    }

    private Point2D.Float randSize() {
        float w = (float) ThreadLocalRandom.current().nextDouble(8, mouseBounds.width);
        float h = (float) ThreadLocalRandom.current().nextDouble(8, mouseBounds.height);
        return new Point2D.Float(w, h);
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        for (QuadTreeItem<Particle> particle : particles) {
            particle.item.draw(g);
        }

        highlightSelectedParticles(g);

        quadtree.draw(g);

        drawShadowText(g, Color.GREEN, "Utilize o botão esquerdo do mouse para criar novos objetos na área verde, ou para remover utilizando os demais botões do mouse.", 2, g.getFont().getSize());
        drawShadowText(g, Color.GREEN, "Objetos encontrados na area verde: " + selectedParticles.size(), 2, g.getFont().getSize() * 2);
        drawShadowText(g, Color.GREEN, "Número total de objetos: " + quadtree.size(), 2, g.getFont().getSize() * 3);
    }

    private void drawShadowText(Graphics g, Color color, String text, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawString(text, x, y);

        g.setColor(color);
        g.drawString(text, x + 1, y + 1);
    }

    private void drawBordered(Graphics g, Color border, GameObject object) {

        int x = (int) object.getPosition().x;
        int y = (int) object.getPosition().y;
        int w = (int) object.getSize().x;
        int h = (int) object.getSize().y;

        g.fillRect(x, y, w, h);
        g.setColor(border);
        g.drawRect(x, y, w, h);
    }

    private void highlightSelectedParticles(Graphics g) {

        g.setColor(Color.GREEN);

        g.drawRect(mouseBounds.x, mouseBounds.y, mouseBounds.width, mouseBounds.height);

        for (QuadTreeItem<Particle> particle : selectedParticles) {

            g.drawRect(
                    particle.bounds.x,
                    particle.bounds.y,
                    particle.bounds.width,
                    particle.bounds.height);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Point position = e.getPoint();

        // Atualiza area de pesquisa para posicao atual do mouse
        mouseBounds.x = position.x - (mouseBounds.width / 2);
        mouseBounds.y = position.y - (mouseBounds.height / 2);

        switch (e.getButton()) {

            case MouseEvent.BUTTON3:
                removeGameObjectsInMouseBounds();
                break;

            default:
                particles.add(createGameObject(position));
                break;
        }

    }

    private void findGameObjectsInMouseBounds() {
        selectedParticles = quadtree.search(mouseBounds);
    }

    private void removeGameObjectsInMouseBounds() {

        List<QuadTreeItem<Particle>> particlesToRemove = quadtree.search(mouseBounds);

        particles.removeAll(particlesToRemove);

        quadtree.resize(particles);
    }

    private QuadTreeItem<Particle> createGameObject(Point position) {

        Particle object = new Particle();
        object.setPosition(new Point2D.Float(position.x, position.y));
        object.setSize(randSize());

        QuadTreeItem<Particle> item = new QuadTreeItem<>();
        item.item = object;
        item.bounds = object.getBounds();

        quadtree.insert(item);

        return item;
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
