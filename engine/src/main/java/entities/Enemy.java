package entities;

import game.GameComponent;
import game.PlayerState;
import ia.BehaviorIA;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import utilities.GameUtil;
import utilities.ResourceManager;

import java.awt.*;
import java.util.List;

@Getter
@Setter
public class Enemy extends Actor {

    private int lifes = 2;
    private final BehaviorIA behavior;
    private ImageAnimation animation;

    @SneakyThrows
    public Enemy(BehaviorIA behavior) {
        this(behavior, 1);
    }

    @SneakyThrows
    public Enemy(BehaviorIA behavior, int enemyType) {
        super();
        this.behavior = behavior;
        this.animation = new ImageAnimation(
                ResourceManager.get().getImage("/image/enemy_" + enemyType + "_1.png"),
                ResourceManager.get().getImage("/image/enemy_" + enemyType + "_2.png"),
                ResourceManager.get().getImage("/image/enemy_" + enemyType + "_3.png")
        );
        this.setImage(animation.getCurrentImage());
    }

    public void clampMove(GameComponent gameComponent) {
        behavior.clampMove(this, gameComponent);
    }

    public void damage(List<PlayerState> players, GameComponent gameComponent) {

        for (PlayerState playerState : players) {
            behavior.damage(this, playerState, gameComponent);
        }

    }

    public void draw(Graphics g, GameComponent gameComponent) {

        g.drawImage(animation.getCurrentImage(),
                getPositionWithOffsetX(),
                getPositionWithOffsetY(), gameComponent);

        animation.updateAnimationFrame();
    }

    public void removeLifes(int qtd) {
        this.lifes = Math.max(0, lifes - qtd);
    }

    // TODO: por enquanto esta aleatorio, mas no futuro o WaveController que vai escolher quais inimigos tem power Up
    public boolean isDropPowerUp() {
        int dropFail = 90;
        int dropRate = 100 - dropFail;
        return GameUtil.getRandomNumber(dropRate, dropFail + dropRate) > dropFail;
    }
}