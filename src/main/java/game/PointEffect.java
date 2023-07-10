package game;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PointEffect {

    private int x, y;
    private int life;
    private int points;
    public void update() {
        life--;
        y--;
    }
}
