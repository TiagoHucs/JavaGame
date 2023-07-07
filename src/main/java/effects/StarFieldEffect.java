package effects;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StarFieldEffect {
    private List<Star> starList;
    private final int w;
    private final int h;

    public StarFieldEffect(int w, int h, int starCount) {
        this.w = w;
        this.h = h;
        loadStars(starCount);
    }

    private void loadStars(int starCount) {

        starList = new ArrayList<Star>(starCount);

        for (int i = 0; i < starCount; i++) {
            float x = (float) (Math.random() * w);
            float y = (float) (Math.random() * h);
            float size = (float) (0.1 + Math.random() * 4.0);
            float speed = (float) (0.1 + Math.random() * 0.8);

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
            graphics.fillOval(
                    (int) star.getX(),
                    (int) star.getY(),
                    (int) star.getSize(),
                    (int) star.getSize());
            star.move(w, h);
        }
    }
}
