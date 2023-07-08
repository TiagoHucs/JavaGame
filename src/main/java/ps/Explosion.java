package ps;

import entities.Ator;
import lombok.SneakyThrows;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Explosion {
    private Particle[] particles;
    private Random rand;
    private BufferedImage particleImage;

    @SneakyThrows
    public Explosion(Ator ator) {
        this.rand = new Random();
        this.particles = new Particle[10];
        this.particleImage = ResourceManager.get().getImage("/image/enemy-ship.png", 5, 5);
        this.init(ator);
    }

    public void init(Ator ator) {
        for (int i = 0; i < particles.length; i++) {
            particles[i] = createParticle(ator);
        }
    }

    private Particle createParticle(Ator ator) {

        int px = ator.getX() + rand.nextInt(ator.getLargura());
        int py = ator.getY() + rand.nextInt(ator.getAltura());

        int sx = rand.nextInt(10);
        int sy = rand.nextInt(10);

        return Particle.builder()
                .position(new Point2D.Float(px, py))
                .speed(new Point2D.Float(sx, sy))
                .build();
    }

    public void update(Graphics g) {
        for (Particle p : particles) {
            p.move();
            g.drawImage(particleImage,
                    (int) p.getPosition().getX(),
                    (int) p.getPosition().getY(),
                    null);
        }
    }

}
