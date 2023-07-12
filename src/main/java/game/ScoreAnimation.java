package game;

import entities.Ator;
import utilities.ResourceManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreAnimation {

    private final Font font = ResourceManager.get().getFont();

    private List<PointEffect> points = new ArrayList<PointEffect>(100);

    public void draw(Graphics g, GameComponent gameComponent) {

        g.setFont(font);
        g.setColor(Color.YELLOW);

        List<PointEffect> pointsToRemove = new ArrayList<>(points.size());

        for (PointEffect p : points) {
            p.update();
            g.drawString(p.getAlert(), p.getX(), p.getY());
            if (p.getLife() < 0) {
                pointsToRemove.add(p);
            }
        }

        points.removeAll(pointsToRemove);

    }


    public void addScore(int pts, Ator ator) {

        PointEffect pointEffect = PointEffect
                .builder()
                .life(10)
                .x((int) (ator.getPosition().x + (ator.getSize().x / 2.0f)))
                .y((int) ator.getPosition().y)
                .points(pts).build();

        points.add(pointEffect);
    }

    public void addExtraLife(Ator ator) {
        PointEffect pointEffect = PointEffect
                .builder()
                .life(100)
                .x((int) (ator.getPosition().x + (ator.getSize().x / 2.0f)))
                .y((int) (ator.getPosition().y))
                .alert("Extra Life!").build();

        points.add(pointEffect);
    }

    public void addPowerUp(Ator ator) {
        PointEffect pointEffect = PointEffect
                .builder()
                .life(50)
                .x((int) (ator.getPosition().x + (ator.getSize().x / 2.0f)))
                .y((int) (ator.getPosition().y))
                .alert("Power Up!").build();

        points.add(pointEffect);
    }

    public void addPowerDown(Ator ator) {
        PointEffect pointEffect = PointEffect
                .builder()
                .life(50)
                .x((int) (ator.getPosition().x + (ator.getSize().x / 2.0f)))
                .y((int) (ator.getPosition().y))
                .alert("Ouch!").build();

        points.add(pointEffect);
    }
}
