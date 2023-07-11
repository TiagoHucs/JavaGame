package game;

import entities.Ator;
import entities.Enemy;
import utilities.ResourceManager;

import java.awt.*;
import java.io.IOException;
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
                .x(ator.getX() + (ator.getLargura() / 2))
                .y(ator.getY())
                .points(pts).build();

        points.add(pointEffect);
    }

    public void addExtraLife(Enemy ator) {
        PointEffect pointEffect = PointEffect
                .builder()
                .life(100)
                .x(ator.getX() + (ator.getLargura() / 2))
                .y(ator.getY())
                .alert("Extra Life!").build();

        points.add(pointEffect);
    }
}
