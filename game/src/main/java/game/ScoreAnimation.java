package game;

import entities.Ator;
import utilities.ResourceManager;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ScoreAnimation {
    private final Font font = ResourceManager.get().getFont();
    private List<PointEffect> points = new LinkedList<PointEffect>();

    public void draw(Graphics g, GameComponent gameComponent) {

        g.setFont(font);

        Iterator<PointEffect> iterator = points.iterator();

        while (iterator.hasNext()) {

            PointEffect p = iterator.next();
            p.update();

            g.setColor(p.getColor());
            g.drawString(p.getText(), p.getX(), p.getY());

            if (p.getLife() < 0) {
                iterator.remove();
            }
        }

    }


    public void addScore(int pts, Ator ator) {

        PointEffect pointEffect = PointEffect
                .scoreBuilder(ator, pts)
                .build();

        points.add(pointEffect);
    }

    public void addExtraLife(Ator ator) {

        PointEffect pointEffect = PointEffect
                .extraLifeBuilder(ator)
                .build();

        points.add(pointEffect);
    }

    public void addPowerUp(Ator ator) {

        PointEffect pointEffect = PointEffect
                .powerUpBuilder(ator)
                .build();

        points.add(pointEffect);
    }

    public void addPowerDown(Ator ator) {

        PointEffect pointEffect = PointEffect
                .powerDownBuilder(ator)
                .build();

        points.add(pointEffect);
    }
}
