package ia;

import entities.GameObject;
import game.GameComponent;
import game.PlayerState;

public interface BehaviorIA {
    void setSpeed(GameObject gameObject, GameComponent gameComponent);
    void clampMove(GameObject gameObject, GameComponent gameComponent);
    void damage(GameObject gameObject, PlayerState playerState, GameComponent gameComponent);
}
