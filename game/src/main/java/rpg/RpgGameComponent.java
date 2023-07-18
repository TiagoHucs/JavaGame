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
    private PlayerActions p1Actions = new PlayerActions();
    private GameObject ball = new GameObject();
    private float f = 5.0f;
    private float x = f;
    private float y = f;

    private GameObject p1;

    private SpriteSheet spriteSheet;

    @Override
    public void init() {

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        p1 = new GameObject();
        p1.setSize(new Point2D.Float(10, 100));
        p1.setPosition(new Point2D.Float(0, getCfg().getGameHeight() / 2));

        spriteSheet = new SpriteSheet("/image/pngegg.png",4,4);
        spriteSheet.play(1);

        // troca de frame por segundos
        spriteSheet.setAnimationSpeed(1);

        p1.setImage(spriteSheet.getCurrentImage());
    }

    @Override
    public void draw(Graphics g2d) {

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        g2d.drawImage(spriteSheet.getCurrentImage(),0,0, null);
    }

    @Override
    public void update(float delta) {
        spriteSheet.updateAnimations(delta);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(KeyEvent.VK_W == e.getKeyCode()){
            p1Actions.setUp(true);
        }
        if(KeyEvent.VK_S == e.getKeyCode()){
            p1Actions.setDown(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(KeyEvent.VK_W == e.getKeyCode()){
            p1Actions.setUp(false);
        }
        if(KeyEvent.VK_S == e.getKeyCode()){
            p1Actions.setDown(false);
        }
    }
}
