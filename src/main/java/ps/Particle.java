package ps;

import lombok.Builder;
import lombok.Getter;

import java.awt.geom.Point2D;

@Builder
@Getter
public class Particle {
    private Point2D.Float position;
    private Point2D.Float speed;

    public void move() {
        this.position.x += speed.x;
        this.position.y += speed.y;
    }
}
