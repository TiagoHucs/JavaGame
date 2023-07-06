package effects;

import entities.Ator;
import java.util.concurrent.ThreadLocalRandom;
public class Shake {
    float decay = 0.8f;
    float maxOffSetX, maxOffSetY = 1.0f;
    float maxRoll = 0.1f;
    float trauma = 0.0f;
    float traumaPower = 2.0f;
    float rotation, offSetX, offSetY = 0.0f;
    public void addTrauma(float amount) {
        this.trauma = Math.min(trauma + amount, 1.0f);
    }
    public void update(float delta, Ator ator) {
        if (trauma > 0.f) {
            trauma = Math.max(trauma - decay * delta, 0.0f);
            shake();
            shake(ator);
        }
    }
    private void shake() {
        float amount = (float) Math.pow(trauma, traumaPower);
        rotation = maxRoll * amount * randRange();
        offSetX = maxOffSetX * amount * randRange();
        offSetY = maxOffSetY * amount * randRange();
    }
    private void shake(Ator ator) {
        ator.setOffSetX((int) offSetX);
        ator.setOffSetY((int) offSetY);
    }

    private float randRange() {
        return (float) ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
    }
}
