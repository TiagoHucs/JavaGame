package effects;

import entities.GameObject;
import lombok.Getter;

@Getter
public class Blink implements Effect {

    private boolean blink = false;
    private boolean invencible = true;
    private int blinkTimer = 30;

    public void reset() {
        blink = false;
        invencible = true;
        blinkTimer = 30;
    }

    public void update(float delta, GameObject gameObject) {

        if (invencible && blinkTimer > 0) {
            blinkTimer--;
            blink = !blink;

        } else {
            blinkTimer = 0;
            invencible = false;
        }
    }

}
