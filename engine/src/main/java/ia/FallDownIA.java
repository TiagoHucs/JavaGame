package ia;

import entities.Actor;
import game.GameComponent;
import game.PlayerState;

import java.awt.geom.Point2D;

public class FallDownIA implements BehaviorIA {

    @Override
    public void setSpeed(Actor actor, GameComponent gameComponent) {
        actor.setVelocity(new Point2D.Float(0.0f, 0.8f));
    }

    @Override
    public void clampMove(Actor actor, GameComponent gameComponent) {
        if (actor.getPosition().y > gameComponent.getCfg().getGameHeight()) {
            actor.getPosition().y = -actor.getSize().y;
        }
    }

    @Override
    public void damage(Actor actor, PlayerState playerState, GameComponent gameComponent) {

        if (actor.getPosition().y > gameComponent.getCfg().getGameHeight()) {
            playerState.getShip().sofreDano(10);
        }
    }
}
