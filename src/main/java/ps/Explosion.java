package ps;

import entities.Ator;
import lombok.SneakyThrows;
import utilities.GameUtil;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Explosion {
    private final int QTD_PARTES = 6;
    private Particle[] particles;
    private BufferedImage particleImage[];

    @SneakyThrows
    public Explosion(Ator ator) {
        this.particles = new Particle[QTD_PARTES];
        this.particleImage = new BufferedImage[QTD_PARTES];
        this.init(ator);
    }

    @SneakyThrows
    public void init(Ator ator) {
        for (int i = 0; i < QTD_PARTES; i++) {
            particles[i] = createParticle(ator);
            particleImage[i] = ResourceManager.get().getImage("/image/player-ship-part-" + (i + 1) + ".png",
                    ator.getLargura(),
                    ator.getAltura());
        }
    }

    private Particle createParticle(Ator ator) {

        int px = ator.getX();
        int py = ator.getY();

        int sx = GameUtil.getRandomNumber(-10, 10);
        int sy = GameUtil.getRandomNumber(-10, 10);

        return Particle.builder()
                .position(new Point2D.Float(px, py))
                .speed(new Point2D.Float(sx, sy))
                .build();
    }

    public void update(Graphics g) {
        for (int i = 0; i < QTD_PARTES; i++) {
            Particle p = particles[i];
            p.move();
            g.drawImage(particleImage[i],
                    (int) p.getPosition().getX(),
                    (int) p.getPosition().getY(),
                    null);
        }
    }

}
