package entities;

import game.GameComponent;
import ia.BehaviorIA;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Enemy extends Ator {

    private int lifes = 2;
    private final BehaviorIA behavior;

    public Enemy(BehaviorIA behavior) {
        this.setImage("/image/enemy_1_1.png");
        this.behavior = behavior;
    }

    public void clampMove(GameComponent gameComponent) {
        behavior.clampMove(this, gameComponent);
    }

    public void draw(Graphics g, GameComponent gameComponent) {
        int px = getX() + getOffSetX();
        int py = getY() + getOffSetY();
        g.drawImage(getImage(), px, py, gameComponent);
    }
}