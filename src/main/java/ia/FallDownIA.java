package ia;

import entities.Ator;
import game.GameComponent;
import utilities.Config;

import java.awt.geom.Point2D;

public class FallDownIA implements BehaviorIA {

    @Override
    public void setPosition(Ator ator, GameComponent gameComponent) {

        Config cfg = gameComponent.getCfg();

        int px = cfg.getRandomGenerator().nextInt(gameComponent.getWidth());
        int py = cfg.getRandomGenerator().nextInt(20) * -50;

        ator.setX(px);
        ator.setY(py);
    }

    @Override
    public void setSpeed(Ator ator, GameComponent gameComponent) {
        ator.setVelocidadeX(0);
        ator.setVelocidadeY(2);
    }

    @Override
    public void clampMove(Ator ator, GameComponent gameComponent) {
        if (ator.getY() > gameComponent.getHeight()) {
            ator.setY(-100);
            ator.setX(gameComponent.getCfg().getRandomGenerator().nextInt(gameComponent.getWidth()));
        }
    }

    @Override
    public void setupEnemy(int enemyID, Ator ator, GameComponent gameComponent) {

        setPosition(ator, gameComponent);
        setSpeed(ator, gameComponent);

        int px = ator.getX() + (ator.getLargura() * enemyID);
        ator.setX(px);
    }
}
