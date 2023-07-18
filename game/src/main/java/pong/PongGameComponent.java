package pong;

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

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        Point2D.Float paddleSize = new Point2D.Float(1, 10);

        p1 = new GameObject();
        p1.setSize(paddleSize);
        p1.setPosition(new Point2D.Float(0, getCfg().getGameHeight() / 2));
        p1Actions.configureButtons(UP, KeyEvent.VK_W);
        p1Actions.configureButtons(DOWN, KeyEvent.VK_S);

        p2 = new GameObject();
        p2.setSize(paddleSize);
        p2.setPosition(new Point2D.Float(getCfg().getGameWidth() - paddleSize.x, getCfg().getGameHeight() / 2));
        p2Actions.configureButtons(UP, KeyEvent.VK_UP);
        p2Actions.configureButtons(DOWN, KeyEvent.VK_DOWN);

        ball = new GameObject();
        ball.setSize(new Point2D.Float(paddleSize.x, paddleSize.x));
        ball.setPosition(getCfg().getGameCenterPosition());
        ball.setVelocityStep(2);
        ball.setVelocity(new Point2D.Float(2,2));
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
    }

    @Override
    public void update(float delta) {
        updatePlayer(p1Actions, p1, delta);
        updatePlayer(p2Actions, p2, delta);
        updateBall(ball, delta);
    }

    private void updateBall(GameObject ball, float delta) {

        ball.move(delta);

        if (ball.getPosition().y > getCfg().getGameHeight()) {
            ball.getVelocity().y = -ball.getVelocityStep();

        } else if (ball.getPosition().y < 0) {
            ball.getVelocity().y = ball.getVelocityStep();
        }

        if (ball.getPosition().x > getCfg().getGameWidth()) {
            ball.getVelocity().x = -ball.getVelocityStep();

        } else if (ball.getPosition().x < 0) {
            ball.getVelocity().x = ball.getVelocityStep();
        }
    }

    private void updatePlayer(PlayerActions actions, GameObject player, float delta) {

        if (actions.isDown()) {
            player.setVelocity(new Point2D.Float(0, 4f));
        }

        if (actions.isUp()) {
            player.setVelocity(new Point2D.Float(0, -4f));
        }

        if (!actions.isUp() && !actions.isDown()) {
            player.stop();
        }

        player.move(delta);
        player.limitToScreenBounds(this);
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
