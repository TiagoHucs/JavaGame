package effects;

import entities.GameObject;

public interface Effect {
    void update(float delta, GameObject gameObject);
}
