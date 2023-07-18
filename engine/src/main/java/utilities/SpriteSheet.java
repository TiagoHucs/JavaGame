package utilities;

import entities.ImageAnimation;
import lombok.SneakyThrows;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private List<ImageAnimation> animations;
    private int currentAnimation = 0;
    private float animationSpeed = 10.0f;
    private float animationFrame = 0;

    @SneakyThrows
    public SpriteSheet(String path, int columns, int rows) {

        this.animations = new ArrayList<ImageAnimation>(rows);

        BufferedImage sheet = ResourceManager.get().getImage(path);
        final int unityWidth = sheet.getWidth() / columns;
        final int unityHeight = sheet.getHeight() / rows;

        for (int y = 0; y < rows; y++) {

            BufferedImage[] animation = new BufferedImage[columns];

            for (int x = 0; x < columns; x++) {

                animation[x] = sheet.getSubimage(
                        x == 0 ? 0 : x * unityWidth,
                        y == 0 ? 0 : y * unityHeight,
                        unityWidth,
                        unityHeight);

            }

            animations.add(new ImageAnimation(animation));
        }

        this.currentAnimation = 0;
        this.animationFrame = 0;
        this.animationSpeed = 10.0f;
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
