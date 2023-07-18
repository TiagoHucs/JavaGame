package utilities;

import entities.GameObject;
import entities.ImageAnimation;
import game.GameComponent;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {
    private List<ImageAnimation> animations;
    private int currentAnimation = 0;
    private float animationSpeed, animationFrame;

    @SneakyThrows
    public SpriteSheet(String path, int columns, int rows, float scale) {

        this.currentAnimation = 0;
        this.animationFrame = 0.0f;
        this.animationSpeed = 0.0f;

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
    }

    public SpriteSheet(String path, int columns, int rows) {
        this(path, columns, rows, 1.0f);
    }

    private static BufferedImage createScaledImage(String path, float scale) throws IOException {

        BufferedImage sheetOriginal = ResourceManager.get().getImage(path);

        if (scale == 1.0f || scale == 0.0f) {
            return sheetOriginal;
        }

        Dimension size = new Dimension(
                (int) (sheetOriginal.getWidth() * scale),
                (int) (sheetOriginal.getHeight() * scale));

        BufferedImage sheetScaled = new BufferedImage(size.width, size.height, sheetOriginal.getType());

        Graphics2D graphics2D = sheetScaled.createGraphics();
        graphics2D.drawImage(sheetOriginal, 0, 0, size.width, size.height, null);
        graphics2D.dispose();

        return sheetScaled;
    }

    public BufferedImage getCurrentImage() {
        return animations.get(currentAnimation).getCurrentImage();
    }

    public void play(int row) {
        this.currentAnimation = Math.min(Math.max(0, row), animations.size() - 1);
    }

    public void updateAnimations(float delta) {

        this.animationFrame += Lerp.twoPoints(animationFrame, animationSpeed, delta);

        if (animationFrame > 1) {
            animations.get(currentAnimation).updateAnimationFrame();
            animationFrame = 0;
        }

    }

    public void updateAnimations(float delta, GameObject gameObject) {

        float speed = Math.abs(gameObject.getVelocity().x)
                    + Math.abs(gameObject.getVelocity().y);

        this.updateAnimations(speed * delta);

        gameObject.setImage(getCurrentImage());
    }

    public void setAnimationSpeed(float animationSpeed) {
        this.animationSpeed = Math.abs(animationSpeed);
    }
}
