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
import java.util.List;
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

            g.fillRect((int) getPosition().x, (int) getPosition().y, (int) getSize().x, (int) getSize().y);

            g.setColor(Color.WHITE);
            g.drawRect((int) getPosition().x, (int) getPosition().y, (int) getSize().x, (int) getSize().y);
        }
    }

    private Quadtree quadtree;
    private Rectangle mouseBounds;
    private List<Particle> particles = new ArrayList<>(1000);
    private List<GameObject> selectedParticles = new ArrayList<>(0);

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        int w = getCfg().getGameWidth();
        int h = getCfg().getGameHeight();

        this.quadtree = new Quadtree(new Rectangle(0, 0, w, h), 4);
        this.mouseBounds = new Rectangle( (w / 2) - 64, (h / 2) - 64, 128, 128);
    }

    @Override
    public void update(float delta) {

        for (Particle p : particles) {
            p.move(delta);
            quadtree.update(p);
            p.collided = quadtree.query(p, new ArrayList<>(8));
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

        for (Particle particle : particles) {
            particle.draw(g);
        }

        highlightSelectedParticles(g);

        quadtree.draw(g);

        drawShadowText(g, Color.GREEN, "Utilize o botão esquerdo do mouse para criar novos objetos na área verde, ou para remover utilizando os demais botões do mouse.", 2, g.getFont().getSize());
        drawShadowText(g, Color.GREEN, "Objetos encontrados na area verde: " + selectedParticles.size(), 2, g.getFont().getSize() * 2);
    }

    private void drawShadowText(Graphics g, Color color, String text, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawString(text, x, y);

        g.setColor(color);
        g.drawString(text, x + 1, y + 1);
    }

    private void highlightSelectedParticles(Graphics g) {

        g.setColor(Color.GREEN);

        g.drawRect(mouseBounds.x, mouseBounds.y, mouseBounds.width, mouseBounds.height);

        for (GameObject particle : selectedParticles) {
            g.drawRect(
                    (int) particle.getPosition().x,
                    (int) particle.getPosition().y,
                    (int) particle.getSize().x,
                    (int) particle.getSize().y);
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
                // Cria particulas
                Particle object = createGameObject(position);
                quadtree.insert(object);
                particles.add(object);
                break;
        }

    }

    private void findGameObjectsInMouseBounds() {

        // Limpa consulta anterior
        selectedParticles.clear();

        // Faz a pesquisa
        quadtree.query(mouseBounds, selectedParticles);
    }

    private void removeGameObjectsInMouseBounds() {

        // Limpa consulta anterior
        selectedParticles.clear();

        // Faz a pesquisa
        quadtree.query(mouseBounds, selectedParticles);

        quadtree.removeAll(selectedParticles);

        particles.removeAll(selectedParticles);

        selectedParticles.clear();
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
