package rpg;

import entities.GameObject;
import game.GameComponent;
import game.GameState;
import game.PlayerActions;
import lombok.SneakyThrows;
import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.view.OrthogonalRenderer;
import utilities.SpriteSheet;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;

public class RpgGameComponent extends GameComponent {
    private PlayerActions playerInput = new PlayerActions();
    private GameObject player;
    private SpriteSheet spriteSheet;
    private Map map;
    private OrthogonalRenderer mapRender;

    @SneakyThrows
    @Override
    public void init() {

        // Carregando mapa
        this.map = new TMXMapReader().readMap(getClass().getResource("/maps/Tiled/sampleMap.tmx"));
        this.mapRender = new OrthogonalRenderer(map);

        this.gameState = new GameState();
        this.gameState.state = GameState.State.PLAY;

        spriteSheet = new SpriteSheet("/image/pngegg.png",4,4, 0.04f);
        spriteSheet.play(0);
        spriteSheet.setAnimationSpeed(1);

        player = new GameObject();
        player.setVelocityStep(1.5f);
        player.setImage(spriteSheet.getCurrentImage());
        player.setPosition(getCfg().getGameCenterPosition());
    }

    @Override
    public void draw(Graphics g2d) {

        g2d.setColor(Color.BLACK);
        g2d.setClip(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());
        g2d.fillRect(0, 0, getCfg().getGameWidth(), getCfg().getGameHeight());

        Iterator<MapLayer> mapLayerIterator = map.iterator();

        while (mapLayerIterator.hasNext()) {
            TileLayer layer = (TileLayer) mapLayerIterator.next();
            mapRender.paintTileLayer((Graphics2D) g2d, layer);
        }

        g2d.drawImage(player.getImage(),
                player.getPositionWithOffsetX(),
                player.getPositionWithOffsetY(), null);
    }

    @Override
    public void update(float delta) {

        spriteSheet.updateAnimations(delta, player);

        // Parar com o efeito "pista de gelo"
        player.stop();

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
