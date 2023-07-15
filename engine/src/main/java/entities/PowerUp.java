package entities;

import game.PlayerState;
import game.ScoreAnimation;

import java.awt.geom.Point2D;

public class PowerUp extends Actor {
    public PowerUp(Actor child) {
        super();
        this.setImage("/image/UI/icon-powerup.png");
        this.setPosition(new Point2D.Float(
                child.getPosition().x,
                child.getPosition().y + child.getSize().y + getSize().y));
        this.setVelocity(new Point2D.Float(0, getSize().y / 4.0f));
    }
    public void levelUp(PlayerState playerState, ScoreAnimation scoreAnimation) {
        playerState.levelUp(scoreAnimation);
    }

    public void levelDown(PlayerState playerState, ScoreAnimation scoreAnimation) {
        playerState.levelDown(scoreAnimation);
    }

}
