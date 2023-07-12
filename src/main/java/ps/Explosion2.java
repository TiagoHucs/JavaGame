package ps;

import entities.Ator;
import entities.ImageAnimation;
import game.GameComponent;
import lombok.SneakyThrows;
import utilities.ResourceManager;

import java.awt.*;

public class Explosion2 {
    private ImageAnimation animation;
    private Ator origem = null;

    @SneakyThrows
    public Explosion2(Ator origem) {
        this.init(origem);
    }

    @SneakyThrows
    public void init(Ator origem) {
        this.origem = origem;
        this.animation = new ImageAnimation(
                ResourceManager.get().getImage("/image/Explosion/explosion-1.png"),
                ResourceManager.get().getImage("/image/Explosion/explosion-2.png"),
                ResourceManager.get().getImage("/image/Explosion/explosion-3.png"),
                ResourceManager.get().getImage("/image/Explosion/explosion-4.png"),
                ResourceManager.get().getImage("/image/Explosion/explosion-5.png"),
                ResourceManager.get().getImage("/image/Explosion/explosion-6.png"),
                ResourceManager.get().getImage("/image/Explosion/explosion-7.png"),
                ResourceManager.get().getImage("/image/Explosion/explosion-8.png")
        );
    }

    public void update(Graphics g, GameComponent game) {
        g.drawImage(animation.getCurrentImage(), (int) origem.getPosition().x, (int) origem.getPosition().y, game);
        animation.updateAnimationFrame();
    }

    public boolean isFinished() {
        return animation.isFinished();
    }

}
