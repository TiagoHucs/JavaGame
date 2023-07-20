package partition;

import entities.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class Quadtree {
    private int capacity;
    private Rectangle bounds;
    private ArrayList<Quadtree> childs = new ArrayList<Quadtree>(4);
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public Quadtree(Rectangle bounds, int capacity) {
        this.bounds = bounds;
        this.capacity = capacity;
    }

    public boolean insert(GameObject gameObject) {

        if (!bounds.intersects(gameObject.getBounds())) {
            return false;
        }

        if (objects.size() < capacity) {
            objects.add(gameObject);
            return true;
        }

        if (childs.isEmpty()) {
            subdivide();
        }

        for (Quadtree child: childs) {

            if (child.insert(gameObject)) {
                return true;
            }

        }

        return false;
    }

    public void subdivide() {

        int x = bounds.x / 2;
        int y = bounds.y / 2;
        int w = bounds.width / 2;
        int h = bounds.height / 2;

        childs.add(new Quadtree(new Rectangle(bounds.x, bounds.y, w, h), this.capacity));
        childs.add(new Quadtree(new Rectangle(bounds.x + w, bounds.y, w, h), this.capacity));
        childs.add(new Quadtree(new Rectangle(bounds.x, bounds.y + h, w, h), this.capacity));
        childs.add(new Quadtree(new Rectangle(bounds.x + w, bounds.y + h, w, h), this.capacity));
    }

    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Color.BLUE);

        for (GameObject gameObject : objects) {

            g.fillRect(
                    gameObject.getPositionWithOffsetX(),
                    gameObject.getPositionWithOffsetY(),
                    (int) gameObject.getSize().y,
                    (int) gameObject.getSize().y);

        }

        for (Quadtree child : childs) {
            child.draw(g);
        }

    }

    public ArrayList<GameObject> query(Rectangle bounds) {
        return null;
    }

}
