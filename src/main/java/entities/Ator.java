package entities;

import effects.Effect;
import game.GameComponent;
import lombok.Getter;
import lombok.Setter;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Ator {

    private Point2D.Float size = new Point2D.Float(1.0f, 1.0f);
    private Point2D.Float position = new Point2D.Float(0.0f, 0.0f);
    private Point2D.Float velocity = new Point2D.Float(0.0f, 0.0f);
    private Point2D.Float maxVelocity = new Point2D.Float(15.0f, 15.0f);
    private Point2D.Float imageOffset = new Point2D.Float(0.0f, 0.0f);
    private Double direction;

    private BufferedImage image;
    private Map<Class, Effect> effectList = new HashMap<Class, Effect>();

    public void addEffect(Effect... effects) {
        for (Effect effect : effects) {
            this.effectList.put(effect.getClass(), effect);
        }
    }

    public <T> T getEffect(Class<T> type) {
        return (T) this.effectList.get(type);
    }

    public void move() {

        this.position.x += this.velocity.x;
        this.position.y += this.velocity.y;

        for (Effect effect : effectList.values()) {
            effect.update(1.0f / 60.0f, this);
        }
    }

    public void setImage(String filename) {
        try {
            setImage(ResourceManager.get().getImage(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        this.size = new Point2D.Float(image.getWidth(), image.getHeight());
    }

    public void increaseXVelocity() {
        this.velocity.x = Math.min(this.maxVelocity.x, this.velocity.x + 1.0f);
    }

    public void decreaseXVelocity() {
        this.velocity.x = Math.max(-this.maxVelocity.x, this.velocity.x - 1.0f);
    }

    public void increaseYVelocity() {
        this.velocity.y = Math.min(this.maxVelocity.y, this.velocity.y + 1.0f);
    }

    public void decreaseYVelocity() {
        this.velocity.y = Math.max(-this.maxVelocity.y, this.velocity.y - 1.0f);
    }

    public void slowDownX() {

        if (this.velocity.x > 0) {
            this.velocity.x--;

        } else if (this.velocity.x < 0) {
            this.velocity.x++;
        }
    }

    public void slowDownY() {
        if (this.velocity.y > 0) {
            this.velocity.y--;

        } else if (this.velocity.y < 0) {
            this.velocity.y++;
        }
    }

    public float getVerticalLimit(GameComponent gameComponent){
        return gameComponent.getHeight() - this.size.y;
    }

    public float getHorizonntalLimit(GameComponent gameComponent){
        return gameComponent.getWidth() - this.size.x;
    }

    public boolean isOutOfScreenX(GameComponent gameComponent) {
        return this.position.x < -size.x || this.position.x > gameComponent.getWidth();
    }
    public boolean isOutOfScreenY(GameComponent gameComponent) {
        return this.position.y > gameComponent.getHeight();
    }

    public boolean isOutOfScreen(GameComponent gameComponent) {
        return isOutOfScreenX(gameComponent) || isOutOfScreenY(gameComponent);
    }

    public int getPositionWithOffsetX() {
        return (int) (getPosition().x + getImageOffset().x);
    }

    public int getPositionWithOffsetY() {
        return (int) (getPosition().y + getImageOffset().y);
    }

    public Double getDirection() {
        return direction;
    }

    public void setDirection(Double direction) {
        this.direction = direction;
    }

    public void drawImage(Graphics g, GameComponent game) {

        if (direction == null) {
            g.drawImage(image, getPositionWithOffsetX(), getPositionWithOffsetY(), game);

        } else {

            Graphics2D g2d =  (Graphics2D) g;

            AffineTransform transform =
                    AffineTransform.getRotateInstance(Math.toRadians(direction),
                    size.getX() / 2.0,
                    size.getX() / 2.0);

            AffineTransformOp filtro = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

            g2d.drawImage(filtro.filter(getImage(), null),
                    getPositionWithOffsetX(),
                    getPositionWithOffsetY(), game);
        }

    }
}
