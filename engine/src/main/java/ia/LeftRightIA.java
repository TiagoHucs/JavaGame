package ia;

import entities.GameObject;
import game.GameComponent;
import game.PlayerState;

public class LeftRightIA implements BehaviorIA {

    @Override
    public void setSpeed(GameObject gameObject, GameComponent gameComponent) {

        if (gameObject.getPosition().x <= 0) {
            gameObject.getVelocity().x = 2.0f;
        } else {
            gameObject.getVelocity().x = -2.0f;
        }

    }
    @Override
    public void clampMove(GameObject gameObject, GameComponent gameComponent) {

        if (gameObject.isOutOfScreenX(gameComponent)) {
            gameObject.getVelocity().x = gameObject.getVelocity().x * -1.0f;
            gameObject.getPosition().y += gameObject.getSize().y;
        }

        if (gameObject.isOutOfScreenY(gameComponent)) {
            gameObject.getPosition().y = -gameObject.getSize().y * 2;
        }

    }

    @Override
    public void damage(GameObject gameObject, PlayerState playerState, GameComponent gameComponent) {
        if (gameObject.getPosition().y > gameComponent.getCfg().getGameHeight()) {
            playerState.getShip().sofreDano(10);
        }
    }

}
