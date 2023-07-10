package ia;

import entities.Ator;
import game.GameComponent;

public interface BehaviorIA {
    void setSpeed(Ator ator, GameComponent gameComponent);
    void clampMove(Ator ator, GameComponent gameComponent);
}
