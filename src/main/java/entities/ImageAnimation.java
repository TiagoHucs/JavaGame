package entities;

import java.awt.image.BufferedImage;

public class ImageAnimation {
    private BufferedImage[] images;

    private int animationFrame;

    public ImageAnimation(BufferedImage... images) {
        this.images = images;
        this.animationFrame = 0;
    }

    public void updateAnimationFrame() {

        animationFrame++;

        if (animationFrame >= images.length) {
            animationFrame = 0;
        }
    }

    public BufferedImage getCurrentImage() {
        return images[animationFrame];
    }

    public int getAnimationFrame() {
        return animationFrame;
    }

    public int getAnimationLength() {
        return images.length;
    }

    public boolean isFinished() {
        return getAnimationFrame() + 1 == getAnimationLength();
    }
}
