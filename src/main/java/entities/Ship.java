package entities;

import effects.Recoil;
import effects.Shake;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ship extends Ator {

    private int lifes = 3;
    private int fireTimer = 0;
    private boolean canFire = true;

    public Ship() {
        this.setImage("/image/player_1.png");
        addEffect(new Shake(), new Recoil());
    }

    public Shot atirar() {
        Shot tiro = new Shot(getX() + (getLargura() / 2), getY());
        this.fireTimer = 10;
        this.canFire = false;
        getEffect(Recoil.class).setSize(10.0f);
        return tiro;
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
