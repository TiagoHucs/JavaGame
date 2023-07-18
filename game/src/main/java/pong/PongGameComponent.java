package pong;

import entities.GameObject;
import game.GameComponent;
import game.GameState;
import game.PlayerActions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public class PongGameComponent extends GameComponent {

    private PlayerActions p1Actions = new PlayerActions();
    private PlayerActions p2Actions = new PlayerActions();
    private GameObject ball = new GameObject();
    private float f = 5.0f;
    private float x = f;
    private float y = f;

    private GameObject p1, p2;

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        p1 = new GameObject();
        p1.setSize(new Point2D.Float(10, 100));
        p1.setPosition(new Point2D.Float(0, getCfg().getGameHeight() / 2));

        p2 = new GameObject();
        p2.setSize(new Point2D.Float(10, 100));
        p2.setPosition(new Point2D.Float(getCfg().getGameWidth() - 10, getCfg().getGameHeight() / 2));
    }

    @Override
    public void draw(Graphics g2d) {

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        g2d.setColor(Color.WHITE);

        g2d.fillRect(p1.getPositionWithOffsetX(), p1.getPositionWithOffsetY(),
                (int) p1.getSize().x,
                (int) p1.getSize().y);

        g2d.fillRect(p2.getPositionWithOffsetX(), p2.getPositionWithOffsetY(),
                (int) p2.getSize().x,
                (int) p2.getSize().y);

        g2d.fillRect(ball.getPositionWithOffsetX(), ball.getPositionWithOffsetY(), 10, 10);
    }

    @Override
    public void update(float delta) {

        if(p1Actions.isDown()){
            p1.setVelocity(new Point2D.Float(0,4f));
        }
        if (p1Actions.isUp()){
            p1.setVelocity(new Point2D.Float(0,-4f));
        }
        if(!p1Actions.isUp() && !p1Actions.isDown()){
            p1.stop();
        }
        p1.move(delta);

        if(p2Actions.isDown()){
            p2.setVelocity(new Point2D.Float(0,4f));
        }

        if (p2Actions.isUp()){
            p2.setVelocity(new Point2D.Float(0,-4f));
        }
        if(!p2Actions.isUp() && !p2Actions.isDown()){
            p2.stop();
        }
        p2.move(delta);

        ball.setVelocity(new Point2D.Float(x, y));
        ball.move(delta);

        if (ball.getPosition().getY() > getCfg().getGameHeight()) {
            y = -f;
        } else if (ball.getPosition().getY() < 0) {
            y = f;
        }

        if (ball.getPosition().getX() > getCfg().getGameWidth()) {
            x = -f;
        } else if (ball.getPosition().getX() < 0) {
            x = f;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(KeyEvent.VK_UP == e.getKeyCode()){
            p2Actions.setUp(true);
        }
        if(KeyEvent.VK_DOWN == e.getKeyCode()){
            p2Actions.setDown(true);
        }
        if(KeyEvent.VK_W == e.getKeyCode()){
            p1Actions.setUp(true);
        }
        if(KeyEvent.VK_S == e.getKeyCode()){
            p1Actions.setDown(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(KeyEvent.VK_UP == e.getKeyCode()){
            p2Actions.setUp(false);
        }
        if(KeyEvent.VK_DOWN == e.getKeyCode()){
            p2Actions.setDown(false);
        }
        if(KeyEvent.VK_W == e.getKeyCode()){
            p1Actions.setUp(false);
        }
        if(KeyEvent.VK_S == e.getKeyCode()){
            p1Actions.setDown(false);
        }
    }
}
