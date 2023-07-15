package game;

import entities.Actor;
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


    public void addScore(int pts, Actor actor) {

        PointEffect pointEffect = PointEffect
                .scoreBuilder(actor, pts)
                .build();

        points.add(pointEffect);
    }

    public void addExtraLife(Actor actor) {

        PointEffect pointEffect = PointEffect
                .extraLifeBuilder(actor)
                .build();

        points.add(pointEffect);
    }

    public void addPowerUp(Actor actor) {

        PointEffect pointEffect = PointEffect
                .powerUpBuilder(actor)
                .build();

        points.add(pointEffect);
    }

    public void addPowerDown(Actor actor) {

        PointEffect pointEffect = PointEffect
                .powerDownBuilder(actor)
                .build();

        points.add(pointEffect);
    }
}
