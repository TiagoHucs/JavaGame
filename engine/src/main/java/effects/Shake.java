package effects;

import entities.GameObject;

import java.awt.geom.Point2D;
import java.util.concurrent.ThreadLocalRandom;

public class Shake implements Effect {
    float decay = 0.8f;
    float maxOffSetX = 1.0f, maxOffSetY = 1.0f;
    float maxRoll = 0.1f;
    float trauma = 0.0f;
    float traumaPower = 2.0f;
    float rotation = 0.0f, offSetX = 0.0f, offSetY = 0.0f;

    public void addTrauma(float amount) {
        this.trauma = Math.min(trauma + amount, 1.0f);
    }

    @Override
    public void update(float delta, GameObject gameObject) {
        if (trauma > 0.f) {
            trauma = Math.max(trauma - decay * delta, 0.0f);
            shake();
            shake(gameObject);
        }
    }

    private void shake() {
        float amount = (float) Math.pow(trauma, traumaPower);
        rotation = maxRoll * amount * randRange();
        offSetX = maxOffSetX * amount * randRange();
        offSetY = maxOffSetY * amount * randRange();
    }

    private void shake(GameObject gameObject) {
        gameObject.setImageOffset(new Point2D.Float(
                offSetX * gameObject.getSize().x,
                offSetY * gameObject.getSize().y));
    }

    private float randRange() {
        return (float) ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
    }
}
