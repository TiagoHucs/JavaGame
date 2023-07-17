package pong;

import entities.Actor;
import game.GameComponent;
import game.GameState;
import utilities.Config;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public class PongGameComponent extends GameComponent {

    private Actor ball = new Actor();
    private float f = 5.0f;
    private float x = f;
    private float y = f;

    private Actor left, right;

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        left = new Actor();
        left.setSize(new Point2D.Float(10, 100));
        left.setPosition(new Point2D.Float(0, getCfg().getGameHeight() / 2));

        right = new Actor();
        right.setSize(new Point2D.Float(10, 100));
        right.setPosition(new Point2D.Float(getCfg().getGameWidth() - 10, getCfg().getGameHeight() / 2));
    }

    @Override
    public void draw(Graphics g2d) {

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        g2d.setColor(Color.WHITE);

        g2d.fillRect(left.getPositionWithOffsetX(), left.getPositionWithOffsetY(),
                (int) left.getSize().x,
                (int) left.getSize().y);

        g2d.fillRect(right.getPositionWithOffsetX(), right.getPositionWithOffsetY(),
                (int) right.getSize().x,
                (int) right.getSize().y);

        g2d.fillRect(ball.getPositionWithOffsetX(), ball.getPositionWithOffsetY(), 10, 10);
    }

    @Override
    public void update(float delta) {

        left.setVelocity(new Point2D.Float(0,
                ball.getPositionWithOffsetY() - left.getPositionWithOffsetY()));
        left.move();

        right.setVelocity(new Point2D.Float(0,
                ball.getPositionWithOffsetY() - right.getPositionWithOffsetY()));
        right.move();

        ball.setVelocity(new Point2D.Float(x, y));
        ball.move();

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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
