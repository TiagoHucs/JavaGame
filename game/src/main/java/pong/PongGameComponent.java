package pong;

import effects.Shake;
import entities.GameObject;
import game.GameComponent;
import game.GameState;
import game.PlayerActions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import static game.PlayerActions.DOWN;
import static game.PlayerActions.UP;

public class PongGameComponent extends GameComponent {
    private PlayerActions p1Actions = new PlayerActions();
    private PlayerActions p2Actions = new PlayerActions();
    private GameObject p1, p2, ball;
    private int p1Points, p2Points;

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        float maxVelocity = 2.0f;
        Point2D.Float center = getCfg().getGameCenterPosition();
        Point2D.Float paddleSize = new Point2D.Float(maxVelocity, 20);

        configurePlayerOne(center, paddleSize);
        configurePlayerTwo(center, paddleSize);
        configureBall(maxVelocity);
    }

    private void configureBall(float maxVelocity) {
        ball = new GameObject();
        ball.setSize(new Point2D.Float(maxVelocity, maxVelocity));
        ball.setPosition(getCfg().getGameCenterPosition());
        ball.setVelocity(new Point2D.Float(maxVelocity, maxVelocity));
    }

    private void configurePlayerTwo(Point2D.Float center, Point2D.Float paddleSize) {
        p2 = new GameObject();
        p2.setSize(paddleSize);
        p2.setPosition(new Point2D.Float(center.x * 2.0f - paddleSize.x, center.y));
        p2.addEffect(new Shake());

        p2Actions.configureButtons(UP, KeyEvent.VK_UP);
        p2Actions.configureButtons(DOWN, KeyEvent.VK_DOWN);
    }

    private void configurePlayerOne(Point2D.Float center, Point2D.Float paddleSize) {
        p1 = new GameObject();
        p1.setSize(paddleSize);
        p1.setPosition(new Point2D.Float(0.0f, center.y));
        p1.addEffect(new Shake());

        p1Actions.configureButtons(UP, KeyEvent.VK_W);
        p1Actions.configureButtons(DOWN, KeyEvent.VK_S);
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

        g2d.fillRect(ball.getPositionWithOffsetX(), ball.getPositionWithOffsetY(),
                (int) ball.getSize().x,
                (int) ball.getSize().y);

        int size = g2d.getFont().getSize();
        int center = getCfg().getGameWidth() / 2;

        g2d.drawString(String.format("%02d", p1Points), center - size * 2, size);
        g2d.drawString(String.format("%02d", p2Points), center + size * 2, size);
    }

    @Override
    public void update(float delta) {
        updatePlayer(p1Actions, p1, delta);
        updatePlayer(p2Actions, p2, delta);
        updateBall(ball, delta);
    }

    private void updateBall(GameObject ball, float delta) {

        ball.move(delta);

        if (ball.getPosition().x > getCfg().getGameWidth()) {
            p1Points++;
            ball.getVelocity().x *= -1;

        } else if (ball.getPosition().x < 0) {
            p2Points++;
            ball.getVelocity().x *= -1;
        }

        if (ball.getPosition().y < 0 || ball.getPosition().y > getCfg().getGameHeight()) {
            ball.getVelocity().y *= -1;
        }

    }

    private void checkCollision(GameObject ball, GameObject paddle) {

        Rectangle ballCollision = new Rectangle(
                (int) ball.getPosition().x,
                (int) ball.getPosition().y,
                (int) ball.getSize().x,
                (int) ball.getSize().y);

        Rectangle paddleCollision = new Rectangle(
                (int) paddle.getPosition().x,
                (int) paddle.getPosition().y,
                (int) paddle.getSize().x,
                (int) paddle.getSize().y);

        if (paddleCollision.intersects(ballCollision)) {
            ball.getVelocity().x *= -1;
            ball.getVelocity().y *= -1;

            paddle.getEffect(Shake.class).addTrauma(10f);
        }

    }

    private void updatePlayer(PlayerActions actions, GameObject player, float delta) {

        if (actions.isDown()) {
            player.getVelocity().y += 4.0f;
        }

        if (actions.isUp()) {
            player.getVelocity().y -= 4.0f;
        }

        player.move(delta);
        player.stop();
        player.limitToScreenBounds(this);

        checkCollision(ball, player);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        p2Actions.keyPressed(e);
        p1Actions.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        p2Actions.keyReleased(e);
        p1Actions.keyReleased(e);
    }
}
