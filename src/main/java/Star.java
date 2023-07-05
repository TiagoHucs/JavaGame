import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Star {

    private int x, y, size, speed;

    public void move(int w, int h) {

        this.y += speed;

        if (y + size > h) {
            this.y -= h + size;
            this.x = (int) (Math.random() * w);
        }
    }
}