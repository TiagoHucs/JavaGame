package entities;

import effects.Effect;
import game.GameComponent;
import lombok.Getter;
import lombok.Setter;
import utilities.ResourceManager;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Ator {

    private int largura, altura;
    private int x, y;
    private int velocidadeX, velocidadeY;
    private int maxVelocity = 15;
    private int offSetX, offSetY;

    private BufferedImage image = null;
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
        this.x += this.velocidadeX;
        this.y += this.velocidadeY;

        for (Effect effect : effectList.values()) {
            effect.update(1.0f / 60.0f, this);
        }

    }

    public void setImage(String filename) {
        try {
            this.image = ResourceManager.get().getImage(filename);
            this.setAltura(image.getHeight());
            this.setLargura(image.getWidth());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseXVelocity() {
        this.velocidadeX = velocidadeX >= maxVelocity ? velocidadeX : velocidadeX + 1;
    }

    public void decreaseXVelocity() {
        this.velocidadeX = velocidadeX <= -maxVelocity ? velocidadeX : velocidadeX - 1;
    }

    public void increaseYVelocity() {
        this.velocidadeY = velocidadeY >= maxVelocity ? velocidadeY : velocidadeY + 1;
    }

    public void decreaseYVelocity() {
        this.velocidadeY = velocidadeY <= -maxVelocity ? velocidadeY : velocidadeY - 1;
    }

    public void slowDownX() {
        if (this.velocidadeX > 0) {
            this.velocidadeX--;
        } else if (this.velocidadeX < 0) {
            this.velocidadeX++;
        }
    }

    public void slowDownY() {
        if (this.velocidadeY > 0) {
            this.velocidadeY--;
        } else if (this.velocidadeY < 0) {
            this.velocidadeY++;
        }
    }

    public int getVerticalLimit(GameComponent gameComponent){
        return gameComponent.getHeight() - getAltura();
    }

    public int getHorizonntalLimit(GameComponent gameComponent){
        return gameComponent.getWidth() - getLargura();
    }

}
