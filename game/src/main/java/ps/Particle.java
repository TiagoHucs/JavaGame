package ps;

import lombok.Builder;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

@Builder
@Getter
public class Particle {

    private BufferedImage image;
    private Point2D.Float position;
    private Point2D.Float speed;
    private float direction;

    public void move() {
        this.position.x += speed.x;
        this.position.y += speed.y;
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform identity = new AffineTransform();
        AffineTransform transform = new AffineTransform();

        transform.setTransform(identity);
        transform.setToTranslation(getPosition().getX(), getPosition().getY());
        transform.rotate( Math.toRadians(direction) );

        g2d.drawImage(image, transform, null);
    }
}
