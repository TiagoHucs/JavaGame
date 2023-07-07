package game;

import entities.Ship;
import entities.Shot;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class PlayerState {

    private int id;
    private int score = 0;
    private final PlayerActions actions = new PlayerActions();
    private final Ship ship = new Ship();
    private final List<Shot> bullets = new LinkedList<Shot>();

    PlayerState(int id) {
        this.id = id;
        ship.setImage("/image/ships/spaceShips_00" + (id + 3) + ".PNG");
    }

    public void draw(Graphics g, GameComponent game) {

        g.setColor(Color.RED);

        for (Shot tiro : bullets) {
            g.fillRect(tiro.getX() + tiro.getOffSetX(), tiro.getY() + tiro.getOffSetY(), tiro.getLargura(), tiro.getAltura());
        }

        g.drawImage(ship.getImage(),
                ship.getX() + ship.getOffSetX(),
                ship.getY() + ship.getOffSetY(), game);

        int lifeStartX = 50;
        int lifeStartY = 20 * (5 * id);

        for (int i = 0; i < ship.getLifes(); i++) {
            g.drawImage(ship.getImage(), lifeStartX * i, lifeStartY, game);
        }

        g.setColor(Color.YELLOW);
        g.setFont(game.getFont());
        g.drawString(score + " Pts", 5, lifeStartY + ship.getImage().getHeight() + 20);
    }

    public void update(GameComponent game) {

        if (actions.isFire() && ship.isCanFire()) {
            game.getSoundManager().playSound("bling.wav");
            bullets.add(ship.atirar());
        }

        for (Shot tiro : bullets) {
            tiro.move();
        }

        //Horizontalmente
        if (actions.isLeft() == actions.isRight()) {
            ship.slowDownX();

        } else if (actions.isLeft()) {
            ship.decreaseXVelocity();

        } else if (actions.isRight()) {
            ship.increaseXVelocity();
        }

        //Verticalmente
        if (actions.isUp() == actions.isDown()) {
            ship.slowDownY();

        } else if (actions.isUp()) {
            ship.decreaseYVelocity();

        } else if (actions.isDown()) {
            ship.increaseYVelocity();
        }

        ship.move();
        ship.checkWeapon();

        if (ship.getX() > ship.getHorizonntalLimi()) {
            ship.setX(ship.getHorizonntalLimi());

        } else if (ship.getX() < 0) {
            ship.setX(0);
        }

        if (ship.getY() > ship.getVerticalLimit()) {
            ship.setY(ship.getVerticalLimit());

        } else if (ship.getY() < 0) {
            ship.setY(0);
        }
    }

    public void addScore(int points) {
        this.score += points;
    }
}
