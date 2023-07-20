package partition;

import entities.GameObject;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Quadtree {
    private int capacity;
    private Rectangle bounds;
    private Set<Quadtree> childs;
    private Set<GameObject> objects;
    private Map<GameObject, Set<Quadtree>> sharedInfo;

    public Quadtree(Rectangle bounds, int capacity) {
        this.bounds = bounds;
        this.capacity = capacity;
        this.childs = new HashSet<Quadtree>(4);
        this.objects = new LinkedHashSet<GameObject>(1000);
        this.sharedInfo = new HashMap<GameObject, Set<Quadtree>>(1000 / 4);
    }
    private Quadtree(Rectangle bounds, Quadtree parent) {
        this(bounds, parent.capacity);
        this.sharedInfo = parent.sharedInfo;
    }
    public void update(GameObject gameObject) {
        this.remove(gameObject);
        this.insert(gameObject);
    }
    public boolean remove(GameObject gameObject) {

        Set<Quadtree> quadtrees = sharedInfo.remove(gameObject);

        quadtrees.forEach(quadtree -> {
            quadtree.objects.remove(gameObject);
        });

        return true;
    }

    public boolean insert(GameObject gameObject) {

        if (!bounds.intersects(gameObject.getBounds())) {
            return false;
        }

        if (objects.size() < capacity) {
            return add(gameObject);
        }

        if (childs.isEmpty()) {
            subdivide();
        }

        childs.forEach(quadtree -> quadtree.insert(gameObject));

        return true;
    }

    private boolean add(GameObject gameObject) {

        if (sharedInfo.containsKey(gameObject)) {
            sharedInfo.get(gameObject).add(this);

        } else {

            Set<Quadtree> quadtreesWithGameObject = new LinkedHashSet<Quadtree>(4 * capacity);
            quadtreesWithGameObject.add(this);

            sharedInfo.put(gameObject, quadtreesWithGameObject);
        }

        return objects.add(gameObject);
    }

    public void subdivide() {
        int w = bounds.width / 2;
        int h = bounds.height / 2;
        childs.add(new Quadtree(new Rectangle(bounds.x, bounds.y, w, h), this));
        childs.add(new Quadtree(new Rectangle(bounds.x + w, bounds.y, w, h), this));
        childs.add(new Quadtree(new Rectangle(bounds.x, bounds.y + h, w, h), this));
        childs.add(new Quadtree(new Rectangle(bounds.x + w, bounds.y + h, w, h), this));
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        childs.forEach(quadtree -> quadtree.draw(g));
    }

    public boolean query(Rectangle area, Set<GameObject> result) {

        if (!this.bounds.intersects(area))
            return !result.isEmpty();

        objects.forEach(gameObject -> {
            if (area.intersects(gameObject.getBounds())) {
                result.add(gameObject);
            }
        });

        childs.forEach(child -> child.query(area, result));

        return !result.isEmpty();
    }

    public boolean query(GameObject object, Set<GameObject> result) {

        final Rectangle objectBounds = object.getBounds();

        if (!objectBounds.intersects(this.bounds))
            return !result.isEmpty();

        objects.forEach(gameObject -> {

            if (object == gameObject || object.equals(gameObject)) {
                return;
            }

            if (objectBounds.intersects(gameObject.getBounds())) {
                result.add(gameObject);
            }
        });

        childs.forEach(child -> {
            child.query(object, result);
        });

        return !result.isEmpty();
    }
}
