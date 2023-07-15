package effects;

import entities.Actor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Recoil implements Effect {

    float size = 0.0f;

    @Override
    public void update(float delta, Actor actor) {
        size = Math.max(0.0f, size - 1.0f);
        recoil(actor);
    }

    private void recoil(Actor actor) {
        actor.getImageOffset().y = size;
    }

}
