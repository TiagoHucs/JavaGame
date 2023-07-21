package partition;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Quadtree<T> {
    private int depth, maxDepth;
    private Rectangle bounds;
    private List<QuadTreeItem<T>> items;
    private Quadtree[] child;
    private Rectangle[] childBound;

    public Quadtree(Rectangle bounds, int maxDepth) {
        this.depth = 0;
        this.maxDepth = maxDepth;
        this.bounds = bounds;
        this.items = new ArrayList<>(1000);
        this.child = new Quadtree[4];
        this.initChildBound();
    }

    private Quadtree(Rectangle bounds, Quadtree<T> parent) {
        this(bounds, parent.maxDepth);
        this.depth = parent.depth + 1;
    }

    public boolean realocate(QuadTreeItem<T> item) {
        return this.remove(item) && this.insert(item);
    }

    public boolean remove(QuadTreeItem<T> item) {
        return item.quadtree.items.remove(item);
    }

    public void removeAll(List<QuadTreeItem<T>> items) {

        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

    }

    public int size() {

        int total = items.size();

        for (Quadtree<T> quadtree : child) {

            if (quadtree != null)
                total += quadtree.size();

        }

        return total;
    }

    public void clear() {

        this.items.clear();

        for (Quadtree<T> quadtree : child) {

            if (quadtree != null)
                quadtree.clear();

        }

        this.child = new Quadtree[4];
    }

    public boolean insert(QuadTreeItem<T> item) {

        for (int i = 0; i < childBound.length; i++) {

            if (childBound[i].contains(item.bounds)) {

                if (depth + 1 < maxDepth) {

                    if (child[i] == null)
                        child[i] = new Quadtree(childBound[i], this);

                    return child[i].insert(item);
                }
            }
        }

        // Cheguei no limite, adicionar aqui mesmo!
        item.quadtree = this;
        return items.add(item);
    }

    private void initChildBound() {

        int x = bounds.x;
        int y = bounds.y;
        int w = bounds.width / 2;
        int h = bounds.height / 2;

        childBound = new Rectangle[4];
        childBound[0] = new Rectangle(x, y, w, h);
        childBound[1] = new Rectangle(x + w, y, w, h);
        childBound[2] = new Rectangle(x, y + h, w, h);
        childBound[3] = new Rectangle(x + w, y + h, w, h);
    }

    public void draw(Graphics g) {

        g.setColor(Color.YELLOW);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        for (Quadtree<T> quadtree : child) {

            if (quadtree != null)
                quadtree.draw(g);

        }

    }

    public List<QuadTreeItem<T>> search(Rectangle area) {

        List<QuadTreeItem<T>> result = new CopyOnWriteArrayList<>();

        if (!this.bounds.intersects(area))
            return result;

        Iterator<QuadTreeItem<T>> iterator = items.iterator();

        while (iterator.hasNext()) {

            QuadTreeItem<T> item = iterator.next();

            if (item.bounds.intersects(area)) {
                result.add(item);
            }
        }

        for (Quadtree<T> quadtree : child) {

            if (quadtree != null)
                result.addAll(quadtree.search(area));

        }

        return result;
    }

    public List<QuadTreeItem<T>> search(QuadTreeItem<T> item) {
        List<QuadTreeItem<T>> result = search(item.bounds);
        result.remove(item);
        return result;
    }

    public void resize(List<QuadTreeItem<T>> items) {

        clear();

        for (QuadTreeItem<T> item : items) {
            insert(item);
        }

    }
}
