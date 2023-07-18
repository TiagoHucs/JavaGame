package utilities;

import entities.ImageAnimation;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private List<ImageAnimation> animations;
    private int currentAnimation = 0;
    private float animationSpeed = 10.0f;
    private float animationFrame = 0;

    @SneakyThrows
    public SpriteSheet(String path, int columns, int rows, float scale) {

        this.animations = new ArrayList<ImageAnimation>(rows);

        BufferedImage sheet = createScaledImage(path, scale);

        Dimension unity = new Dimension(sheet.getWidth() / columns,sheet.getHeight() / rows);

        for (int frame = 0; frame < rows; frame++) {

            BufferedImage[] animation = new BufferedImage[columns];

            for (int id = 0; id < columns; id++) {

                animation[id] = sheet.getSubimage(
                        id == 0 ? 0 : id * unity.width,
                        frame == 0 ? 0 : frame * unity.height,
                        unity.width,
                        unity.height);

            }

            animations.add(new ImageAnimation(animation));
        }

        this.currentAnimation = 0;
        this.animationFrame = 0;
        this.animationSpeed = 10.0f;
    }

    public SpriteSheet(String path, int columns, int rows) {
        this(path, columns, rows, 1.0f);
    }

    private static BufferedImage createScaledImage(String path, float scale) throws IOException {

        BufferedImage sheetOriginal = ResourceManager.get().getImage(path);

        if (scale == 1.0f || scale == 0.0f) {
            return sheetOriginal;
        }

        Dimension newSize = new Dimension(
                (int) (sheetOriginal.getWidth() * scale),
                (int) (sheetOriginal.getHeight() * scale));

        BufferedImage bufferedImageResized = new BufferedImage(newSize.width, newSize.height, sheetOriginal.getType());

        Graphics2D graphics2D = bufferedImageResized.createGraphics();
        graphics2D.drawImage(sheetOriginal, 0, 0, newSize.width, newSize.height, null);
        graphics2D.dispose();

        return bufferedImageResized;
    }

    public BufferedImage getCurrentImage() {
        return animations.get(currentAnimation).getCurrentImage();
    }

    public void play(int row) {
        this.currentAnimation = Math.min(row, animations.size());
    }

    public void updateAnimations(float delta) {

        this.animationFrame += Lerp.twoPoints(animationFrame, animationSpeed, delta);

        if (animationFrame > 1) {
            animations.get(currentAnimation).updateAnimationFrame();
            animationFrame = 0;
        }

    }
    public void setAnimationSpeed(float animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
}
