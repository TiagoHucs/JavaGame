package entities;

import effects.Recoil;
import effects.Shake;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ship extends Ator {

    private int energia = 100;

    public Ship() {
        this.setImage("/image/ships/spaceShips_003.PNG");
        addEffect(new Shake(), new Recoil());
    }

    public Shot atirar() {
        Shot tiro = new Shot(getX() + (getLargura() / 2), getY());
        this.energia -= 1;
        getEffect(Recoil.class).setSize(10.0f);
        return tiro;
    }

    public void sofreDano(int dano) {
        this.energia -= dano;
        getEffect(Shake.class).addTrauma((float) dano / 10);
    }

}
