package ia;

import entities.Ator;
import game.GameComponent;

public interface BehaviorIA {
    void setPosition(Ator ator, GameComponent gameComponent);
    void setSpeed(Ator ator, GameComponent gameComponent);
    void clampMove(Ator ator, GameComponent gameComponent);
    void setupEnemy(int enemyID, Ator ator, GameComponent gameComponent);
}
