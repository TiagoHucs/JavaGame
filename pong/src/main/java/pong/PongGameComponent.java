package pong;

import effects.Shake;
import entities.GameObject;
import game.CollisionController;
import game.GameComponent;
import game.GameState;
import game.PlayerActions;
import pong.menu.PongMenu;

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
    private CollisionController collisionController = new CollisionController();

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;
        this.menu = new PongMenu(this);

        float ballSize = 2.0f;
        Point2D.Float center = getCfg().getGameCenterPosition();
        Point2D.Float paddleSize = new Point2D.Float(ballSize, ballSize * 10.0f);

        configureBall(center, ballSize);
        configurePlayerOne(center, paddleSize);
        configurePlayerTwo(center, paddleSize);
    }

    private void configureBall(Point2D.Float center, float ballSize) {
        ball = new GameObject();
        ball.setSize(new Point2D.Float(ballSize, ballSize));
        ball.setPosition(center);
        ball.setVelocity(new Point2D.Float(ballSize, ballSize));
    }

    private void configurePlayerOne(Point2D.Float center, Point2D.Float paddleSize) {

        p1 = new GameObject();
        p1.setSize(paddleSize);
        p1.setPosition(new Point2D.Float(0.0f, center.y));
        p1.addEffect(new Shake());

        p1Actions.configureButtons(UP, KeyEvent.VK_W);
        p1Actions.configureButtons(DOWN, KeyEvent.VK_S);

        collisionController.watchForCollision(ball, p1, (a, b) -> {

            a.getVelocity().x *= -1;
            a.getVelocity().y *= -1;
            b.getEffect(Shake.class).addTrauma(b.getSize().y);

            return false;
        });
    }

    private void configurePlayerTwo(Point2D.Float center, Point2D.Float paddleSize) {

        p2 = new GameObject();
        p2.setSize(paddleSize);
        p2.setPosition(new Point2D.Float(center.x * 2.0f - paddleSize.x, center.y));
        p2.addEffect(new Shake());

        p2Actions.configureButtons(UP, KeyEvent.VK_UP);
        p2Actions.configureButtons(DOWN, KeyEvent.VK_DOWN);

        collisionController.watchForCollision(ball, p2, (a, b) -> {

            a.getVelocity().x *= -1;
            a.getVelocity().y *= -1;
            b.getEffect(Shake.class).addTrauma(b.getSize().y);

            return false;
        });
    }

    @Override
    public void draw(Graphics g2d) {

        switch (gameState.state) {

            case MENU:
                drawMenu(g2d);
                break;

            case PLAY:
                drawGame(g2d);
                break;
        }
    }

    public void drawGame(Graphics g2d) {

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

    public void drawMenu(Graphics g2d) {
        menu.draw(g2d);
    }

    @Override
    public void update(float delta) {

        switch (gameState.state) {

            case MENU:
                menu.update(this, delta);
                break;

            case PLAY:
                updatePlayer(p1Actions, p1, delta);
                updatePlayer(p2Actions, p2, delta);
                updateBall(ball, delta);
                collisionController.doBruteForceCheck();
                break;

            case QUIT:
                System.exit(0);
                break;
        }

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

        if (ball.getPosition().y > getCfg().getGameHeight() || ball.getPosition().y < 0) {
            ball.getVelocity().y *= -1;
        }

    }

    private void updatePlayer(PlayerActions actions, GameObject player, float delta) {

        // Player é só uma unidade mais rápido que a bolinha
        float ballVelocity = Math.abs(ball.getVelocity().y) + 1.0f;

        if (actions.isDown()) {
            player.getVelocity().y += ballVelocity;
        }

        if (actions.isUp()) {
            player.getVelocity().y -= ballVelocity;
        }

        player.move(delta);
        player.stop();
        player.limitToScreenBounds(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                gameState.state = GameState.State.MENU;
                break;
        }

        switch (gameState.state) {

            case MENU:
                menu.keyPressed(e);
                break;

            case PLAY:
                p2Actions.keyPressed(e);
                p1Actions.keyPressed(e);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (gameState.state) {

            case MENU:
                menu.keyReleased(e);
                break;

            case PLAY:
                p2Actions.keyReleased(e);
                p1Actions.keyReleased(e);
                break;
        }

    }
}
