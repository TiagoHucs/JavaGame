package entities;

import game.GameComponent;
import lombok.Getter;
import lombok.Setter;
import utilities.Config;
import utilities.GameUtil;

import java.awt.*;

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

    public void clampMove(Config cfg) {
        if (getY() > cfg.getAlturaTela()) {
            setY(-100);
            setX(cfg.getRandomGenerator().nextInt(cfg.getResolution()));
        }
    }

    public void draw(Graphics g, GameComponent gameComponent) {

        int px = getX() + getOffSetX();
        int py = getY() + getOffSetY();

        int sy = getY() + getAltura();

        if (sy < 0) {
            g.setColor(Color.RED);
            g.drawString("V", getX(), 10);

        } else {
            g.drawImage(getImage(), px, py, gameComponent);
        }

    }
}