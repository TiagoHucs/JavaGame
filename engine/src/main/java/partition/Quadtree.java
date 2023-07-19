package partition;

import java.awt.*;
import java.util.ArrayList;

public class Quadtree {

    private Rectangle size;

    private int capacity;

    private Quadtree[] childs = new Quadtree[4];

    private boolean divided = false;

    private ArrayList<Point> points = new ArrayList<>();

    public Quadtree(Rectangle size, int capacity) {
        this.size = size;
        this.capacity = capacity;
    }

    public boolean insert(Point point) {

        if (!size.contains(point)) {
            return false;
        }

        if (points.size() < capacity) {
            points.add(point);
            return true;
        }

        if (!divided) {
            subdivide();
        }

        for (int i = 0; i < childs.length; i++) {

            if (childs[i].insert(point))
                return true;
        }

        return false;
    }

    public void subdivide() {

        int x = size.x;
        int y = size.y;
        int w = size.width;
        int h = size.height;

        childs[0] = new Quadtree(
                new Rectangle((x + w) / 2, (y - h) / 2, w / 2, h / 2),
                this.capacity);

        childs[1] = new Quadtree(
                new Rectangle((x - w) / 2, (y - h) / 2, w / 2, h / 2),
                this.capacity);

        childs[2] = new Quadtree(
                new Rectangle((x + w) / 2, (y + h) / 2, w / 2, h / 2),
                this.capacity);

        childs[3] = new Quadtree(
                new Rectangle((x - w) / 2, (y + h) / 2, w / 2, h / 2),
                this.capacity);

        divided = true;
    }

    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.drawRect(size.x, size.y, size.width * 2, size.height * 2);

        g.setColor(Color.BLUE);

        for (Point p : points) {
            g.fillRect(p.x, p.y, 2,2);
        }

        if (divided) {
            for (int i = 0; i < childs.length; i++) {
                childs[i].draw(g);
            }
        }
    }

    public void query() {

    }


}
