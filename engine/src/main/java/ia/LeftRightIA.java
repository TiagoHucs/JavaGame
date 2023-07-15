package ia;

import entities.Actor;
import game.GameComponent;
import game.PlayerState;

public class LeftRightIA implements BehaviorIA {

    @Override
    public void setSpeed(Actor actor, GameComponent gameComponent) {

        if (actor.getPosition().x <= 0) {
            actor.getVelocity().x = 2.0f;
        } else {
            actor.getVelocity().x = -2.0f;
        }

    }
    @Override
    public void clampMove(Actor actor, GameComponent gameComponent) {

        if (actor.isOutOfScreenX(gameComponent)) {
            actor.getVelocity().x = actor.getVelocity().x * -1.0f;
            actor.getPosition().y += actor.getSize().y;
        }

        if (actor.isOutOfScreenY(gameComponent)) {
            actor.getPosition().y = -actor.getSize().y * 2;
        }

    }

    @Override
    public void damage(Actor actor, PlayerState playerState, GameComponent gameComponent) {
        if (actor.getPosition().y > gameComponent.getCfg().getGameHeight()) {
            playerState.getShip().sofreDano(10);
        }
    }

}
