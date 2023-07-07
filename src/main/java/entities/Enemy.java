package entities;

import lombok.Getter;
import lombok.Setter;
import utilities.GameUtil;

@Getter
@Setter
public class Enemy extends Ator {

    private int lifes = 2;

    public Enemy(int x, int y) {
        this.setImage("/image/ships/spaceShips_00" +
                GameUtil.getRandomNumber(4, 9)
                + ".PNG");
        this.setVelocidadeY(2);
        this.setX(x);
        this.setY(y);
    }

}