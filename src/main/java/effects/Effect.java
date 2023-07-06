package effects;

import entities.Ator;

public interface Effect {
    void update(float delta, Ator ator);
}
