package ia;

import entities.GameObject;
import game.GameComponent;
import game.PlayerState;

import java.awt.geom.Point2D;

public class FallDownIA implements BehaviorIA {

    @Override
    public void setSpeed(GameObject gameObject, GameComponent gameComponent) {
        gameObject.setVelocity(new Point2D.Float(0.0f, 0.8f));
    }

    @Override
    public void clampMove(GameObject gameObject, GameComponent gameComponent) {
        if (gameObject.getPosition().y > gameComponent.getCfg().getGameHeight()) {
            gameObject.getPosition().y = -gameObject.getSize().y;
        }
    }

    @Override
    public void damage(GameObject gameObject, PlayerState playerState, GameComponent gameComponent) {

        if (gameObject.getPosition().y > gameComponent.getCfg().getGameHeight()) {
            playerState.getShip().sofreDano(10);
        }
    }
}
