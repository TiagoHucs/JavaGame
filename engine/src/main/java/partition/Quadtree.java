package partition;

import entities.GameObject;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Quadtree<T> {
    private int capacity;
    private Rectangle bounds;
    private List<Quadtree<T>> childs;
    private List<QuadTreeItem<T>> items;

    public Quadtree(Rectangle bounds, int capacity) {
        this.bounds = bounds;
        this.capacity = capacity;
        this.childs = new ArrayList<>(4);
        this.items = new ArrayList<>(1000);
    }

    private Quadtree(Rectangle bounds, Quadtree<T> parent) {
        this(bounds, parent.capacity);
    }

    public boolean realocate(QuadTreeItem<T> item) {
        this.remove(item);
        return this.insert(item);
    }

    public boolean remove(QuadTreeItem<T> item) {
        return item.quadtree.items.remove(item);
    }

    public void removeAll(List<QuadTreeItem<T>> items) {

        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

    }

    public void clear() {

        this.items.clear();

        for (int i = 0; i < this.childs.size(); i++) {
            this.childs.get(i).clear();
        }

        this.childs.clear();
    }

    public boolean insert(QuadTreeItem<T> item) {

        if (!bounds.intersects(item.bounds)) {
            return false;
        }

        if (items.size() < capacity) {
            item.quadtree = this;
            return items.add(item);
        }

        if (childs.isEmpty()) {
            subdivide();
        }

        for (int i = 0; i < this.childs.size(); i++) {

            if (childs.get(i).insert(item))
                return true;
        }

        return false;
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

    public boolean query(Rectangle area, List<QuadTreeItem<T>> result) {

        if (!this.bounds.intersects(area))
            return !result.isEmpty();

        for (int i = 0; i < this.items.size(); i++) {

            QuadTreeItem<T> item = this.items.get(i);

            if (area.intersects(item.bounds)) {
                result.add(item);
            }

        }

        for (int i = 0; i < this.childs.size(); i++) {
            this.childs.get(i).query(area, result);
        }

        return !result.isEmpty();
    }

    public boolean query(QuadTreeItem<T> item, List<QuadTreeItem<T>> result) {

        if (!this.bounds.intersects(item.bounds))
            return !result.isEmpty();

        for (int i = 0; i < this.items.size(); i++) {

            QuadTreeItem<T> quadtreeItem = this.items.get(i);

            if (item == quadtreeItem || item.equals(quadtreeItem)) {
                continue;
            }

            if (item.bounds.intersects(quadtreeItem.bounds)) {
                result.add(quadtreeItem);
            }
        }

        for (int i = 0; i < this.childs.size(); i++) {
            this.childs.get(i).query(item, result);
        }

        return !result.isEmpty();
    }
}
