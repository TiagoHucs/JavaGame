package effects;

import game.GameComponent;
import lombok.SneakyThrows;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class StarFieldEffect {
    private List<Star> starList;
    private final int w;
    private final int h;
    public static final Color STAR_COLOR = new Color(117, 67, 121);
    public static final Color SPACE_COLOR = new Color(25, 6, 31);
    private BufferedImage stars1, stars2, nebula1, nebula2, staticBackground;
    private BufferedImage[] layers;

    public StarFieldEffect(GameComponent gameComponent, int starCount) {
        this.w = gameComponent.getWidth();
        this.h = gameComponent.getHeight();
        // this.createDynamicStars(starCount);
        this.createStaticStars();
    }

    @SneakyThrows
    private void createStaticStars() {

        this.stars1 = ResourceManager.get().getImage("/image/Space Background/stars_1.png");
        this.stars2 = ResourceManager.get().getImage("/image/Space Background/stars_2.png");
        this.nebula1 = ResourceManager.get().getImage("/image/Space Background/nebula_1.png");
        this.nebula2 = ResourceManager.get().getImage("/image/Space Background/nebula_2.png");

        this.createStaticLayerImage(4);
        this.createStaticBackgroundImage();
    }

    private void createStaticLayerImage(int layerCount) {

        layers = new BufferedImage[layerCount];

        for (int i = 0; i < layerCount; i++) {

            layers[i] = new BufferedImage(w, h, stars1.getType());

            Graphics graphics = layers[i].getGraphics();
            graphics.setColor(SPACE_COLOR);

            switch (i) {
                case 0:
                    renderLayer(graphics, stars1);
                    break;
                case 1:
                    renderLayer(graphics, stars2);
                    break;
                case 2:
                    renderLayer(graphics, nebula1);
                    break;
                case 3:
                    renderLayer(graphics, nebula2);
                    break;
            }

            graphics.dispose();
        }
    }

    private void createStaticBackgroundImage() {

        staticBackground = new BufferedImage(w, h, stars1.getType());

        Graphics graphics = staticBackground.getGraphics();
        graphics.setColor(SPACE_COLOR);

        for (BufferedImage layer : layers) {
            graphics.drawImage(layer, 0, 0, null);
        }

        graphics.dispose();
    }

    private void createDynamicStars(int starCount) {

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

    public void draw(Graphics graphics, GameComponent game) {
        // drawDynamicStars(graphics);
        drawStaticStars(graphics, game, false);
    }

    private void drawStaticStars(Graphics graphics, GameComponent game, boolean parallaxEffect) {

        // TODO: no futuro pode ter efeito parallax
        if (parallaxEffect) {

            for (int i = 0; i < layers.length; i++) {
                graphics.drawImage(layers[i], 0, 0, game);
            }

        } else {
            graphics.drawImage(staticBackground, 0, 0, game);
        }

    }

    private void renderLayer(Graphics graphics, BufferedImage image) {

        int offsetX = image.getWidth();
        int offsetY = image.getHeight();
        int limitX = 1 + (w / offsetX);
        int limitY = 1 + (h / offsetY);
        int sy = 0;

        for (int y = 0; y < limitY; y++) {
            int sx = 0;
            for (int x = 0; x < limitX; x++) {
                graphics.drawImage(image, sx, sy, null);
                sx += offsetX;
            }
            sy += offsetY;
        }
    }

    private void drawDynamicStars(Graphics graphics) {

        graphics.setColor(STAR_COLOR);

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
