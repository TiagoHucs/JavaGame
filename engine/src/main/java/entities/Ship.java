package entities;

import effects.Recoil;
import effects.Shake;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Ship extends GameObject {

    private int lifes = 3;
    private int fireTimer = 0;
    private boolean canFire = true;

    public Ship() {
        super();
        this.setImage("/image/player_1.png");
        addEffect(new Shake(), new Recoil());
    }

    public List<Shot> atirar(int numberOfShoots) {

        float px = getSize().x / 2.0f;
        float py = getSize().y / 2.0f;

        if (numberOfShoots > 1) {
            px -= numberOfShoots * 5.0f;
        }

        List<Shot> tiros = new ArrayList<Shot>(numberOfShoots);

        for (int i = 0; i < numberOfShoots; i++) {
            Shot tiro = new Shot(getPosition().x + px, getPosition().y - py);
            tiros.add(tiro);
            px += tiro.getSize().x;
        }

        this.fireTimer = 10;
        this.canFire = false;

        getEffect(Recoil.class).setSize((getSize().y / getSize().x) * 10.0f);

        return tiros;
    }

    public void sofreDano(int dano) {
        getEffect(Shake.class).addTrauma((float) dano / 10);
        lifes = lifes > 0 ? lifes -1 : 0;
    }

    public void checkWeapon() {

        fireTimer--;

        if (fireTimer < 0) {
            canFire = true;
            fireTimer = 0;
        }
    }
}
