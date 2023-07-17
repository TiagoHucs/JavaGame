package effects;

import entities.GameObject;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Recoil implements Effect {

    float size = 0.0f;

    @Override
    public void update(float delta, GameObject gameObject) {
        size = Math.max(0.0f, size - 1.0f);
        recoil(gameObject);
    }

    private void recoil(GameObject gameObject) {
        gameObject.getImageOffset().y = size;
    }

}
