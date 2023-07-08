package ps;

import entities.Ator;
import game.GameComponent;
import lombok.SneakyThrows;
import utilities.GameUtil;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Explosion {
    private final int QTD_PARTES = 6;
    private List<Particle> particles;

    @SneakyThrows
    public Explosion(Ator ator) {
        this.particles = new ArrayList<Particle>(QTD_PARTES);
        this.init(ator);
    }

    @SneakyThrows
    public void init(Ator ator) {
        for (int i = 0; i < QTD_PARTES; i++) {

            BufferedImage image = ResourceManager.get().getImage("/image/player-ship-part-" + (i + 1) + ".png",
                    ator.getLargura(),
                    ator.getAltura());

            particles.add(createParticle(ator, image));
        }
    }

    private Particle createParticle(Ator ator, BufferedImage image) {

        int px = ator.getX();
        int py = ator.getY();

        int sx = safeSpeed(GameUtil.getRandomNumber(-10, 10));
        int sy = safeSpeed(GameUtil.getRandomNumber(-10, 10));

        return Particle.builder()
                .position(new Point2D.Float(px, py))
                .speed(new Point2D.Float(sx, sy))
                .image(image)
                .direction(GameUtil.getRandomNumber(0, 360))
                .build();
    }

    private int safeSpeed(int sx) {
        if (sx == 0) {
            return safeSpeed(GameUtil.getRandomNumber(-10, 10));
        }
        return sx;
    }

    public void update(Graphics g, GameComponent game) {

        List<Particle> particlesToRemove = new ArrayList<Particle>(particles.size());

        for (Particle p : particles) {

            p.move();
            p.draw(g);

            if (p.getPosition().getX() < 0 || p.getPosition().getX() > game.getCfg().getLarguraTela()) {
                particlesToRemove.add(p);

            } else if (p.getPosition().getY() < 0 || p.getPosition().getY() > game.getCfg().getAlturaTela()) {
                particlesToRemove.add(p);
            }
        }

        particles.removeAll(particlesToRemove);
    }

    public boolean isFinished() {
        return particles.isEmpty();
    }

}
