package ia;

import entities.Ator;
import game.GameComponent;
import game.PlayerState;

public class LeftRightIA implements BehaviorIA {

    @Override
    public void setSpeed(Ator ator, GameComponent gameComponent) {

        if (ator.getPosition().x <= 0) {
            ator.getVelocity().x = 2.0f;
        } else {
            ator.getVelocity().x = -2.0f;
        }

    }
    @Override
    public void clampMove(Ator ator, GameComponent gameComponent) {

        if (ator.isOutOfScreenX(gameComponent)) {
            ator.getVelocity().x = ator.getVelocity().x * -1.0f;
            ator.getPosition().y += ator.getSize().y;
        }

        if (ator.isOutOfScreenY(gameComponent)) {
            ator.getPosition().y = -ator.getSize().y * 2;
        }

    }

    @Override
    public void damage(Ator ator, PlayerState playerState, GameComponent gameComponent) {
        if (ator.getPosition().y > gameComponent.getCfg().getGameHeight()) {
            playerState.getShip().sofreDano(10);
        }
    }

}
