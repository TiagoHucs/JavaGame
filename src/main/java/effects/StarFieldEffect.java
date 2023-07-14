package effects;

import game.GameComponent;
import lombok.SneakyThrows;
import utilities.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StarFieldEffect {
    private final int w;
    private final int h;
    public static final Color STAR_COLOR = new Color(117, 67, 121);
    public static final Color SPACE_COLOR = new Color(25, 6, 31);
    private BufferedImage layers[], staticBackground;
    private List<Star> starList;

    public StarFieldEffect(GameComponent gameComponent, boolean staticBackground) {

        this.w = gameComponent.getCfg().getGameWidth();
        this.h = gameComponent.getCfg().getGameHeight();

        if (staticBackground) {
            this.createStaticStars();
        } else {
            this.createDynamicStars(256);
        }

    }

    @SneakyThrows
    private void createStaticStars() {

        // Ordem sugerida, do mais longe para o mais perto.
        List<BufferedImage> images = Arrays.asList(
                ResourceManager.get().getImage("/image/Space Background/nebula_2.png"),
                ResourceManager.get().getImage("/image/Space Background/nebula_1.png"),
                ResourceManager.get().getImage("/image/Space Background/stars_2.png"),
                ResourceManager.get().getImage("/image/Space Background/stars_1.png")
        );

        this.createStaticLayerImage(images);
        this.createStaticBackgroundImage();
    }

    private void createStaticLayerImage(List<BufferedImage> images) {

        layers = new BufferedImage[images.size()];

        for (BufferedImage layerImage : images) {
            int i = images.indexOf(layerImage);
            layers[i] = new BufferedImage(w, h, layerImage.getType());
            Graphics graphics = layers[i].getGraphics();
            renderLayer(graphics, layerImage);
            graphics.dispose();
        }
    }

    private void createStaticBackgroundImage() {

        staticBackground = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = staticBackground.getGraphics();
        graphics.setColor(SPACE_COLOR);
        graphics.fillRect(0, 0, w, h);

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

        if (starList == null) {
            drawStaticStars(graphics, game, false);

        } else {
            drawDynamicStars(graphics);
        }

    }

    private void drawStaticStars(Graphics graphics, GameComponent game, boolean parallaxEffect) {

        // TODO: no futuro pode ter efeito parallax
        if (parallaxEffect) {

            for (BufferedImage layer : layers) {
                graphics.drawImage(layer, 0, 0, game);
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

        graphics.setColor(SPACE_COLOR);
        graphics.fillRect(0, 0, w, h);

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
