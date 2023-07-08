package ia;

import entities.Ator;
import game.GameComponent;
import utilities.Config;
import utilities.GameUtil;

import java.awt.geom.Point2D;

public class FallSideIA implements BehaviorIA {

    @Override
    public void setPosition(Ator ator, GameComponent gameComponent) {

        Config cfg = gameComponent.getCfg();

        // Padrao, lado esquerdo da tela
        int px = 0;
        int py = cfg.getRandomGenerator().nextInt(cfg.getAlturaTela() / 2);

        // Lado direito da tela
        if (cfg.getRandomGenerator().nextInt(100) > 50) {
            px = cfg.getLarguraTela();
        }

        ator.setX(px);
        ator.setY(py);
    }

    @Override
    public void setSpeed(Ator ator, GameComponent gameComponent) {

        int speed = GameUtil.getRandomNumber(2, 20);

        if (ator.getX() <= 0) {
            ator.setVelocidadeX(speed);
        } else {
            ator.setVelocidadeX(-speed);
        }

        ator.setVelocidadeY(2);

    }
    @Override
    public void clampMove(Ator ator, GameComponent gameComponent) {

        if (ator.getY() > gameComponent.getHeight()) {
            setPosition(ator, gameComponent);
        }

        if (gameComponent.getCfg().getRandomGenerator().nextInt(100) > 90) {
            ator.increaseYVelocity();
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
