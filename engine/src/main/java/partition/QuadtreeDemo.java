package partition;

import engine.GameWindow;
import entities.GameObject;
import game.GameComponent;
import game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuadtreeDemo extends GameComponent {

    private Quadtree quadtree;

    private final Random rand = new Random();

    private Rectangle queryBounds = new Rectangle(0,0, 256, 256);
    private List<GameObject> queryResult = new ArrayList<>();


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

    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        quadtree.draw(g);

        if (!queryResult.isEmpty()) {

            g.setColor(Color.GREEN);

            g.drawRect(queryBounds.x, queryBounds.y, queryBounds.width, queryBounds.height);

            for (GameObject gameObject : queryResult) {

                g.drawRect(
                        gameObject.getPositionWithOffsetX(),
                        gameObject.getPositionWithOffsetY(),
                        (int) gameObject.getSize().y,
                        (int) gameObject.getSize().y);

            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Point position = e.getPoint();

        GameObject object = createGameObject(position);

        quadtree.insert(object);

        findGameObjectsInMouseBounds(position);
    }

    private void findGameObjectsInMouseBounds(Point position) {

        // Limpa consulta anterior
        queryResult.clear();

        // Atualiza area de pesquisa para posicao atual do mouse
        queryBounds.x = position.x - (queryBounds.width / 2);
        queryBounds.y = position.y - (queryBounds.height / 2);

        // Faz a pesquisa
        quadtree.query(queryBounds, queryResult);

        System.out.println("Encontrei " + queryResult.size() + " objetos.");
    }

    private GameObject createGameObject(Point position) {

        GameObject object = new GameObject();

        object.setPosition(new Point2D.Float(position.x, position.y));

        object.setSize(new Point2D.Float(
                4 + rand.nextInt(64),
                4 + rand.nextInt(64)));

        return object;
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new GameWindow(Toolkit.getDefaultToolkit().getScreenSize(), new QuadtreeDemo()).play();
    }
}
