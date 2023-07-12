package entities;

import game.GameComponent;

public class Shot extends Ator {
    public Shot(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setImage("/image/projectile_1.png");
        this.setVelocidadeY(-10);
    }
    public boolean isOffScreen(GameComponent gameComponent) {
        // Tiro só se move vertical por enquanto
        return getY() < -getLargura() ||
               getY() > gameComponent.getHeight() + getAltura();
    }

}
