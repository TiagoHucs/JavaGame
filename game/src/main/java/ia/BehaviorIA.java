package ia;

import entities.Ator;
import game.GameComponent;
import game.PlayerState;

public interface BehaviorIA {
    void setSpeed(Ator ator, GameComponent gameComponent);
    void clampMove(Ator ator, GameComponent gameComponent);
    void damage(Ator ator, PlayerState playerState, GameComponent gameComponent);
}
