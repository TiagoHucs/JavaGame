package ia;

import entities.Ator;
import game.GameComponent;
import utilities.Config;

import java.awt.geom.Point2D;

public class FallDownIA implements BehaviorIA {

    @Override
    public void setSpeed(Ator ator, GameComponent gameComponent) {
        ator.setVelocidadeX(0);
        ator.setVelocidadeY(2);
    }

    @Override
    public void clampMove(Ator ator, GameComponent gameComponent) {
        if (ator.getY() > gameComponent.getHeight()) {
            ator.setY(-100);
        }
    }
}
