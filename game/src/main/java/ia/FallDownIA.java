package ia;

import entities.Ator;
import game.GameComponent;

import java.awt.geom.Point2D;

public class FallDownIA implements BehaviorIA {

    @Override
    public void setSpeed(Ator ator, GameComponent gameComponent) {
        ator.setVelocity(new Point2D.Float(0.0f, 0.8f));
    }

    @Override
    public void clampMove(Ator ator, GameComponent gameComponent) {
        if (ator.getPosition().y > gameComponent.getCfg().getGameHeight()) {
            ator.getPosition().y = -ator.getSize().y;
        }
    }
}
