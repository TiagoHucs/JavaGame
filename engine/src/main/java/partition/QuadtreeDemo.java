package partition;

import engine.GameWindow;
import game.GameComponent;
import game.GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class QuadtreeDemo extends GameComponent {

    private Quadtree quadtree;

    @Override
    public void init() {
        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        Rectangle screenBounds = new Rectangle(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());
        this.quadtree = new Quadtree(screenBounds, 10);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        quadtree.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        Random rand = new Random();

        for (int i = 0; i < getCfg().getGameWidth(); i++) {
            quadtree.insert(new Point(
                    rand.nextInt(getCfg().getGameWidth()),
                    rand.nextInt(getCfg().getGameHeight())));
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        new GameWindow(Toolkit.getDefaultToolkit().getScreenSize(), new QuadtreeDemo()).play();
    }
}
