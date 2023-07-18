package rpg;

import engine.GameWindow;
import entities.GameObject;
import game.GameComponent;
import game.GameState;
import game.PlayerActions;
import utilities.SpriteSheet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

public class RpgGameComponent extends GameComponent {
    private PlayerActions playerInput = new PlayerActions();
    private GameObject player;
    private SpriteSheet spriteSheet;

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        spriteSheet = new SpriteSheet("/image/pngegg.png",4,4, 0.1f);
        spriteSheet.play(0);
        spriteSheet.setAnimationSpeed(1);

        player = new GameObject();
        player.setImage(spriteSheet.getCurrentImage());
        player.setPosition(new Point2D.Float(getCfg().getGameWidth() / 2, getCfg().getGameHeight() / 2));
    }

    @Override
    public void draw(Graphics g2d) {

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        g2d.drawImage(spriteSheet.getCurrentImage(),
                player.getPositionWithOffsetX(),
                player.getPositionWithOffsetY(), null);
    }

    @Override
    public void update(float delta) {

        float speed = Math.abs(player.getVelocity().x) + Math.abs(player.getVelocity().y);

        spriteSheet.updateAnimations( speed * delta);

        if (playerInput.isUp()) {
            player.decreaseYVelocity();
        }

        if (playerInput.isDown()) {
            player.increaseYVelocity();
        }

        if (playerInput.isLeft()) {
            player.decreaseXVelocity();
        }

        if (playerInput.isRight()) {
            player.increaseXVelocity();
        }

        player.move(delta);
        player.limitToScreenBounds(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        playerInput.keyPressed(e);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                spriteSheet.play(0);
                break;
            case KeyEvent.VK_RIGHT:
                spriteSheet.play(1);
                break;
            case KeyEvent.VK_LEFT:
                spriteSheet.play(2);
                break;
            case KeyEvent.VK_UP:
                spriteSheet.play(3);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        playerInput.keyReleased(e);
    }
}
