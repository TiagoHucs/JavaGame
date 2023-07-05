package effects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StarFieldEffect {
    private List<Star> starList;
    private int w, h;

    public StarFieldEffect(int w, int h, int starCount) {
        this.w = w;
        this.h = h;
        loadStars(starCount);
    }

    private void loadStars(int starCount) {

        starList = new ArrayList<Star>(starCount);

        for (int i = 0; i < starCount; i++) {
            int x = (int) (Math.random() * w);
            int y = (int) (Math.random() * h);
            int size = (int) (0.1 + Math.random() * 5);
            int speed = (int)(0.1 + Math.random() * 3);

            starList.add(Star.builder()
                    .x(x)
                    .y(y)
                    .size(size)
                    .speed(speed).build());
        }

    }
    public void draw(Graphics graphics) {

        graphics.setColor(Color.WHITE);

        for (Star star : starList) {
            graphics.fillOval(star.getX(), star.getY(), star.getSize(),star.getSize());
            star.move(w, h);
        }
    }
}
