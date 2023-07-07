package entities;

import utilities.GameUtil;

public class Enemy extends Ator {

    public Enemy(int x, int y) {
        this.setImage("/image/ships/spaceShips_00" +
                GameUtil.getRandomNumber(4, 9)
                + ".PNG");
        this.setVelocidadeY(2);
        this.setX(x);
        this.setY(y);
    }

}