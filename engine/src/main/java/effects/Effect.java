package effects;

import entities.Actor;

public interface Effect {
    void update(float delta, Actor actor);
}
