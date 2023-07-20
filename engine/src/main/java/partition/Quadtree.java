package partition;

import entities.GameObject;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Quadtree {
    private int capacity;
    private Rectangle bounds;
    private List<Quadtree> childs;
    private List<GameObject> objects;
    public Quadtree(Rectangle bounds, int capacity) {
        this.bounds = bounds;
        this.capacity = capacity;
        this.childs = new ArrayList<>(4);
        this.objects = new ArrayList<>(1000);
    }
    private Quadtree(Rectangle bounds, Quadtree parent) {
        this(bounds, parent.capacity);
    }
    public void update(GameObject gameObject) {
        this.remove(gameObject);
        this.insert(gameObject);
    }
    public boolean remove(GameObject gameObject) {

        if (this.bounds.intersects(gameObject.getBounds())) {

            this.objects.remove(gameObject);

            for (int i = 0; i < this.childs.size(); i++) {
                this.childs.get(i).remove(gameObject);
            }

            return true;
        }

        return removeBruteForce(gameObject);
    }

    private boolean removeBruteForce(GameObject gameObject) {

        System.out.println("Objeto saiu da grid, usando força bruta!");

        this.objects.remove(gameObject);

        for (int i = 0; i < this.childs.size(); i++) {
            this.childs.get(i).removeBruteForce(gameObject);
        }

        return true;
    }

    public void removeAll(List<GameObject> gameObjectList) {

        for (int i = 0; i < gameObjectList.size(); i++) {
            this.remove(gameObjectList.get(i));
        }

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

        for (int i = 0; i < this.childs.size(); i++) {
            this.childs.get(i).insert(gameObject);
        }

        return true;
    }

    public void subdivide() {
        int x = bounds.x;
        int y = bounds.y;
        int w = bounds.width / 2;
        int h = bounds.height / 2;
        childs.add(new Quadtree(new Rectangle(x, y, w, h), this));
        childs.add(new Quadtree(new Rectangle(x + w, y, w, h), this));
        childs.add(new Quadtree(new Rectangle(x, y + h, w, h), this));
        childs.add(new Quadtree(new Rectangle(x + w, y + h, w, h), this));
    }

    public void draw(Graphics g) {

        g.setColor(Color.YELLOW);

        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        for (int i = 0; i < this.childs.size(); i++) {
            this.childs.get(i).draw(g);
        }
    }

    public boolean query(Rectangle area, List<GameObject> result) {

        if (!this.bounds.intersects(area))
            return !result.isEmpty();

        for (int i = 0; i < this.objects.size(); i++) {

            GameObject gameObject = this.objects.get(i);

            if (area.intersects(gameObject.getBounds())) {
                result.add(gameObject);
            }

        }

        for (int i = 0; i < this.childs.size(); i++) {
            this.childs.get(i).query(area, result);
        }

        return !result.isEmpty();
    }

    public boolean query(GameObject object, List<GameObject> result) {

        Rectangle objectBounds = object.getBounds();

        if (!objectBounds.intersects(this.bounds))
            return !result.isEmpty();

        for (int i = 0; i < this.objects.size(); i++) {

            GameObject gameObject = this.objects.get(i);

            if (object == gameObject || object.equals(gameObject)) {
                continue;
            }

            if (objectBounds.intersects(gameObject.getBounds())) {
                result.add(gameObject);
            }
        }

        for (int i = 0; i < this.childs.size(); i++) {
            this.childs.get(i).query(object, result);
        }

        return !result.isEmpty();
    }
}
