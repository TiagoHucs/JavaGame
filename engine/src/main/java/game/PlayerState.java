package game;

import effects.Blink;
import entities.Ship;
import entities.Shot;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

@Getter
@Setter
public class PlayerState {

    private int id;
    private int score = 0;
    private int pointsToExtraLife = 10000;

    private final PlayerActions actions = new PlayerActions();
    private final Ship ship = new Ship();
    private final LinkedList<Shot> bullets = new LinkedList<Shot>();
    private Blink blinkEffect = new Blink();

    private int playerAnimationFrame = 1;
    private int shootsPerFire = 1;

    private BufferedImage lifeIcon;

    @SneakyThrows
    PlayerState(int id) {
        this.id = id;
        ship.setImage("/imgs/SpaceShip-1.png.png");
        ship.addEffect(blinkEffect);
        lifeIcon = ResourceManager.get().getImage("/image/UI/icon-ship.png");
    }

    public void draw(Graphics g) {
        drawBullets(g);
        drawShip(g);
        drawHUD(g);
    }

    private void drawHUD(Graphics g) {

        int lifeStartX = lifeIcon.getWidth();
        int lifeStartY = lifeIcon.getHeight() + (lifeIcon.getHeight() * id);

        for (int i = 0; i < ship.getLifes(); i++) {
            g.drawImage(lifeIcon, lifeStartX * i, lifeStartY, null);
        }
    }

    private void drawShip(Graphics g) {

        updateAnimationFrame();

        if (blinkEffect.isBlink()) {

            g.setColor(Color.GRAY);

            g.drawArc(ship.getPositionWithOffsetX(),
                    ship.getPositionWithOffsetY(),
                    (int) ship.getSize().x,
                    (int) ship.getSize().y,
                    0, 360);

        } else {
            ship.drawImage(g);
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

    private void drawBullets(Graphics g) {

        for (Shot tiro : bullets) {
            tiro.drawImage(g);
        }
    }

    public void update(GameComponent game, float delta) {
        updateBullets(game, delta);
        updateShip(game, delta);
    }

    private void updateShip(GameComponent game, float delta) {

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

        ship.move(delta);
        ship.limitToScreenBounds(game);
        ship.checkWeapon();
    }

    private void updateBullets(GameComponent game, float delta) {

        if (actions.isFire() && ship.isCanFire()) {
            game.getSoundManager().playSound("bling.wav");
            bullets.addAll(ship.atirar(shootsPerFire));
        }

        Iterator<Shot> shotIterator = bullets.iterator();

        while (shotIterator.hasNext()) {

            Shot tiro = shotIterator.next();

            if (tiro.isOffScreen(game)) {
                shotIterator.remove();

            } else {
                tiro.move(delta);
            }
        }
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void addExtraLife(int lifes) {
        int total = this.ship.getLifes() + lifes;
        this.ship.setLifes(total);
    }

    public void levelUp(ScoreAnimation scoreAnimation) {
        // Level 5 é o maximo de tiros
        this.shootsPerFire = Math.min(shootsPerFire + 1, 5);

        scoreAnimation.addPowerUp(ship);
    }

    public void levelDown(ScoreAnimation scoreAnimation) {

        this.shootsPerFire = Math.max(1, shootsPerFire - 1);

        scoreAnimation.addPowerDown(ship);
    }

    public boolean extraLife() {
        return score % pointsToExtraLife == 0;
    }
}
