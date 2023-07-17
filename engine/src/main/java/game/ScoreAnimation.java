package game;

import entities.GameObject;
import utilities.ResourceManager;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ScoreAnimation {
    private final Font font = ResourceManager.get().getFont();
    private List<PointEffect> points = new LinkedList<PointEffect>();

    public void draw(Graphics g) {

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


    public void addScore(int pts, GameObject gameObject) {

        PointEffect pointEffect = PointEffect
                .scoreBuilder(gameObject, pts)
                .build();

        points.add(pointEffect);
    }

    public void addExtraLife(GameObject gameObject) {

        PointEffect pointEffect = PointEffect
                .extraLifeBuilder(gameObject)
                .build();

        points.add(pointEffect);
    }

    public void addPowerUp(GameObject gameObject) {

        PointEffect pointEffect = PointEffect
                .powerUpBuilder(gameObject)
                .build();

        points.add(pointEffect);
    }

    public void addPowerDown(GameObject gameObject) {

        PointEffect pointEffect = PointEffect
                .powerDownBuilder(gameObject)
                .build();

        points.add(pointEffect);
    }
}
