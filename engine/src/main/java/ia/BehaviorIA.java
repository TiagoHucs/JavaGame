package ia;

import entities.Actor;
import game.GameComponent;
import game.PlayerState;

public interface BehaviorIA {
    void setSpeed(Actor actor, GameComponent gameComponent);
    void clampMove(Actor actor, GameComponent gameComponent);
    void damage(Actor actor, PlayerState playerState, GameComponent gameComponent);
}
