package partition;

import entities.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Quadtree {
    private int capacity;
    private Rectangle bounds;
    private List<Quadtree> childs = new ArrayList<Quadtree>(4);
    private List<GameObject> objects = new LinkedList<>();

    public Quadtree(Rectangle bounds, int capacity) {
        this.bounds = bounds;
        this.capacity = capacity;
    }

    public boolean insert(GameObject gameObject) {

        if (!bounds.intersects(gameObject.getBounds())) {
            return false;
        }

        if (objects.size() < capacity) {
            return objects.add(gameObject);
        }

        if (childs.isEmpty()) {
            subdivide();
        }

        for (Quadtree child: childs) {

            if (child.insert(gameObject)) {
                return true;
            }

        }

        return objects.add(gameObject);
    }

    public void subdivide() {

        int w = bounds.width / 2;
        int h = bounds.height / 2;

        childs.add(new Quadtree(new Rectangle(bounds.x, bounds.y, w, h), capacity));
        childs.add(new Quadtree(new Rectangle(bounds.x + w, bounds.y, w, h), capacity));
        childs.add(new Quadtree(new Rectangle(bounds.x, bounds.y + h, w, h), capacity));
        childs.add(new Quadtree(new Rectangle(bounds.x + w, bounds.y + h, w, h), capacity));
    }

    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Color.BLUE);

        for (int i = 0; i < objects.size(); i++) {

            GameObject gameObject = objects.get(i);

            g.fillRect(
                    gameObject.getPositionWithOffsetX(),
                    gameObject.getPositionWithOffsetY(),
                    (int) gameObject.getSize().y,
                    (int) gameObject.getSize().y);

        }

        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).draw(g);
        }

    }

    public void query(Rectangle bounds, List<GameObject> result) {

        if (!this.bounds.intersects(bounds))
            return;

        for (GameObject gameObject : objects) {
            if (gameObject.getBounds().intersects(bounds)) {
                result.add(gameObject);
            }
        }

        for (Quadtree child : childs) {
            child.query(bounds, result);
        }

    }

}
