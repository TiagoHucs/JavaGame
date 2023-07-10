package entities;

public class Shot extends Ator {
    public Shot(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setImage("/image/projectile_1.png");
        this.setVelocidadeY(-10);
    }

}
