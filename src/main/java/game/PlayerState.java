package game;

import effects.Blink;
import entities.Ship;
import entities.Shot;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
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
    private Blink blinkEffect = new Blink();

    private int playerAnimationFrame = 1;

    PlayerState(int id) {
        this.id = id;
        ship.setImage("/image/player_1.png");
        ship.addEffect(blinkEffect);
    }

    public void draw(Graphics g, GameComponent game) {
        drawBullets(g, game);
        drawShip(g, game);
        drawHUD(g, game);
    }

    private void drawHUD(Graphics g, GameComponent game) {

        int lifeStartX = 50;
        int lifeStartY = 20 * (5 * id);

        for (int i = 0; i < ship.getLifes(); i++) {
            g.drawImage(ship.getImage(), lifeStartX * i, lifeStartY, game);
        }

        g.setColor(Color.YELLOW);
        g.setFont(game.getCfg().getFont());
        g.drawString(score + " Pts", 5, lifeStartY + ship.getImage().getHeight() + 20);
    }

    private void drawShip(Graphics g, GameComponent game) {

        updateAnimationFrame();

        if (blinkEffect.isBlink()) {

            int radius = (ship.getAltura() + ship.getLargura())/ 2;

            g.drawArc(ship.getX() + ship.getOffSetX(),
                    ship.getY() + ship.getOffSetY(),
                    radius, radius,
                    0, 360);
        } else {
            g.drawImage(ship.getImage(),
                    ship.getX() + ship.getOffSetX(),
                    ship.getY() + ship.getOffSetY(), game);
        }
    }

    private void updateAnimationFrame() {

        if (playerAnimationFrame > 3) {
            playerAnimationFrame = 1;
        }

        if (this.getActions().isLeft()) {
            ship.setImage("/image/player_left-" + playerAnimationFrame + ".png");

        } else if (this.getActions().isRight()) {
            ship.setImage("/image/player_right-" + playerAnimationFrame + ".png");

        } else {
            ship.setImage("/image/player_" + playerAnimationFrame + ".png");
        }

        playerAnimationFrame++;
    }

    private void drawBullets(Graphics g, GameComponent game) {

        g.setColor(Color.RED);

        for (Shot tiro : bullets) {
            g.drawImage(tiro.getImage(),
                    tiro.getX() + tiro.getOffSetX(),
                    tiro.getY() + tiro.getOffSetY(),
                    game);
        }
    }

    public void update(GameComponent game) {
        updateBullets(game);
        updateShip(game);
    }

    private void updateShip(GameComponent game) {

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

        if (ship.getX() > ship.getHorizonntalLimit(game)) {
            ship.setX(ship.getHorizonntalLimit(game));

        } else if (ship.getX() < 0) {
            ship.setX(0);
        }

        if (ship.getY() > ship.getVerticalLimit(game)) {
            ship.setY(ship.getVerticalLimit(game));

        } else if (ship.getY() < 0) {
            ship.setY(0);
        }
    }

    private void updateBullets(GameComponent game) {

        if (actions.isFire() && ship.isCanFire()) {
            game.getSoundManager().playSound("bling.wav");
            bullets.add(ship.atirar());
        }

        for (Shot tiro : bullets) {
            tiro.move();
        }
    }

    public void addScore(int points) {
        this.score += points;
    }
}
