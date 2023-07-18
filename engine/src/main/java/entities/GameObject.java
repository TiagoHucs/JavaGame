package entities;

import effects.Effect;
import engine.GameWindow;
import game.GameComponent;
import lombok.Getter;
import lombok.Setter;
import utilities.Lerp;
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
public class GameObject {

    private Point2D.Float size = new Point2D.Float(1.0f, 1.0f);
    private Point2D.Float position = new Point2D.Float(0.0f, 0.0f);
    private Point2D.Float velocity = new Point2D.Float(0.0f, 0.0f);
    private Point2D.Float maxVelocity = new Point2D.Float(15.0f, 15.0f);
    private Point2D.Float imageOffset = new Point2D.Float(0.0f, 0.0f);
    private Double direction;
    private float velocityStep = 1.0f;

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

    public void move(float delta) {

        this.position.x = Lerp.twoPoints(position.x, position.x + this.velocity.x * GameWindow.FPS, delta);
        this.position.y = Lerp.twoPoints(position.y, position.y + this.velocity.y * GameWindow.FPS, delta);

        for (Effect effect : effectList.values()) {
            effect.update(delta, this);
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
        this.velocity.x = Math.min(this.maxVelocity.x, this.velocity.x + velocityStep);
    }

    public void decreaseXVelocity() {
        this.velocity.x = Math.max(-this.maxVelocity.x, this.velocity.x - velocityStep);
    }

    public void increaseYVelocity() {
        this.velocity.y = Math.min(this.maxVelocity.y, this.velocity.y + velocityStep);
    }

    public void decreaseYVelocity() {
        this.velocity.y = Math.max(-this.maxVelocity.y, this.velocity.y - velocityStep);
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

    public void stop(){
        this.velocity.x = 0;
        this.velocity.y = 0;
    }
    public float getVerticalLimit(GameComponent gameComponent){
        return gameComponent.getCfg().getGameHeight() - this.size.y;
    }

    public float getHorizonntalLimit(GameComponent gameComponent){
        return gameComponent.getCfg().getGameWidth() - this.size.x;
    }

    public boolean isOutOfScreenX(GameComponent gameComponent) {
        return this.position.x < -size.x || this.position.x > gameComponent.getCfg().getGameWidth();
    }
    public boolean isOutOfScreenY(GameComponent gameComponent) {
        return this.position.y > gameComponent.getCfg().getGameHeight();
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

    public void drawImage(Graphics g) {

        if (direction == null) {
            g.drawImage(image, getPositionWithOffsetX(), getPositionWithOffsetY(), null);

        } else {

            Graphics2D g2d =  (Graphics2D) g;

            AffineTransform transform =
                    AffineTransform.getRotateInstance(Math.toRadians(direction),
                    size.x / 2.0,
                    size.y / 2.0);

            AffineTransformOp filtro = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

            g2d.drawImage(filtro.filter(image, null),
                    (int) getPosition().x,
                    (int) getPosition().y, null);
        }

    }

    public void limitToScreenBounds(GameComponent gameComponent) {

        float maxPositionX = getHorizonntalLimit(gameComponent);
        float maxPositionY = getVerticalLimit(gameComponent);

        if (position.x < 0) {
            position.x = 0;
            velocity.x = 0;

        } else if (position.x > maxPositionX) {
            position.x = maxPositionX;
            velocity.x = 0;
        }

        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;

        } else if (position.y > maxPositionY) {
            position.y = maxPositionY;
            velocity.y = 0;
        }
    }
}
