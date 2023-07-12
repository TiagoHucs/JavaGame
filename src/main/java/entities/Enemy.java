package entities;

import game.GameComponent;
import ia.BehaviorIA;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import utilities.ResourceManager;

import java.awt.*;

@Getter
@Setter
public class Enemy extends Ator {

    private int lifes = 2;
    private final BehaviorIA behavior;
    private ImageAnimation animation;

    @SneakyThrows
    public Enemy(BehaviorIA behavior) {
        this.behavior = behavior;
        this.animation = new ImageAnimation(
                ResourceManager.get().getImage("/image/enemy_1_1.png"),
                ResourceManager.get().getImage("/image/enemy_1_2.png"),
                ResourceManager.get().getImage("/image/enemy_1_3.png")
        );
        this.setImage(animation.getCurrentImage());
    }

    public void clampMove(GameComponent gameComponent) {
        behavior.clampMove(this, gameComponent);
    }

    public void draw(Graphics g, GameComponent gameComponent) {

        int px = getX() + getOffSetX();
        int py = getY() + getOffSetY();

        g.drawImage(animation.getCurrentImage(), px, py, gameComponent);

        animation.updateAnimationFrame();
    }

    public void removeLifes(int qtd) {
        this.lifes = Math.max(0, lifes - qtd);
    }
}