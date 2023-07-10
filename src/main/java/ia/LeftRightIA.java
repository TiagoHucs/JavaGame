package ia;

import entities.Ator;
import game.GameComponent;
import utilities.Config;

import java.awt.geom.Point2D;

public class LeftRightIA implements BehaviorIA {

    @Override
    public void setSpeed(Ator ator, GameComponent gameComponent) {

        if (ator.getX() <= 0) {
            ator.setVelocidadeX(2);
        } else {
            ator.setVelocidadeX(-2);
        }

    }
    @Override
    public void clampMove(Ator ator, GameComponent gameComponent) {

        if (ator.getX() < -ator.getLargura() || ator.getX() > gameComponent.getWidth()) {
            ator.setVelocidadeX(ator.getVelocidadeX() * -1);
            ator.setY(ator.getY() + ator.getAltura());
        }

        if (ator.getY() > gameComponent.getHeight()) {
            ator.setY(-100);
        }

    }

}
