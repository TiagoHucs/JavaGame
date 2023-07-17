package entities;

import game.GameComponent;

import java.awt.geom.Point2D;

public class Shot extends GameObject {
    public Shot(float x, float y) {
        super();
        this.setPosition(new Point2D.Float(x, y));
        this.setVelocity(new Point2D.Float(0.0f, -10.0f));
        this.setImage("/image/projectile_1.png");
    }
    public boolean isOffScreen(GameComponent gameComponent) {
        // Tiro só se move vertical por enquanto
        return this.getPosition().y < -this.getSize().x ||
                this.getPosition().y > gameComponent.getCfg().getGameHeight() + this.getSize().y;
    }

}
