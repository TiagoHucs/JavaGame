package game;


import entities.Ator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.security.cert.CertPathBuilder;

@Getter
@Setter
@Builder
public class PointEffect {

    private int x, y;
    private int life;
    private String text;
    private Color color;

    public static PointEffectBuilder scoreBuilder(Ator ator, int pts) {

        float px = ator.getPosition().x + (ator.getSize().x / 2.0f);
        float py = ator.getPosition().y;

        return PointEffect.builder()
                .life(10)
                .color(Color.YELLOW)
                .text(pts + "pts")
                .x((int) px)
                .y((int) py);
    }

    public static PointEffectBuilder extraLifeBuilder(Ator ator) {

        float px = ator.getPosition().x + (ator.getSize().x / 2.0f);
        float py = ator.getPosition().y;

        return PointEffect.builder()
                .life(100)
                .color(Color.CYAN)
                .text("Extra Life!")
                .x((int) px)
                .y((int) py);
    }

    public static PointEffectBuilder powerUpBuilder(Ator ator) {

        float px = ator.getPosition().x + (ator.getSize().x / 2.0f);
        float py = ator.getPosition().y;

        return PointEffect.builder()
                .life(50)
                .color(Color.GREEN)
                .text("Power Up!")
                .x((int) px)
                .y((int) py);
    }

    public static PointEffectBuilder powerDownBuilder(Ator ator) {

        float px = ator.getPosition().x + (ator.getSize().x / 2.0f);
        float py = ator.getPosition().y;

        return PointEffect.builder()
                .life(50)
                .color(Color.RED)
                .text("Danger!")
                .x((int) px)
                .y((int) py);
    }

    public void update() {
        life--;
        y--;
    }
}
