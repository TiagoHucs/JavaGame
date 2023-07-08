package ia;

import entities.Ator;
import game.GameComponent;
import utilities.Config;

import java.awt.geom.Point2D;

public class LeftRightIA implements BehaviorIA {

    @Override
    public void setPosition(Ator ator, GameComponent gameComponent) {

        Config cfg = gameComponent.getCfg();

        // Padrao, lado esquerdo da tela
        int px = 0;
        int py = cfg.getRandomGenerator().nextInt(gameComponent.getHeight() / 2);

        // Lado direito da tela
        if (cfg.getRandomGenerator().nextInt(100) > 50) {
            px = cfg.getLarguraTela();
        }

        ator.setX(px);
        ator.setY(py);
    }

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

        if (ator.getX() < 0 || ator.getX() > gameComponent.getWidth()) {
            ator.setVelocidadeX(ator.getVelocidadeX() * -1);
            ator.setY(ator.getY() + ator.getAltura());
        }

        if (ator.getY() > gameComponent.getHeight()) {
            setPosition(ator, gameComponent);
            setSpeed(ator, gameComponent);
        }

    }

    @Override
    public void setupEnemy(int enemyID, Ator ator, GameComponent gameComponent) {

        setPosition(ator, gameComponent);
        setSpeed(ator, gameComponent);

        int py = ator.getY() + (ator.getAltura() * enemyID);
        ator.setY(py);
    }


}
