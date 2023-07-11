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
    private String alert;
    public void update() {
        life--;
        y--;
    }

    public String getAlert() {

        if (alert == null) {
            alert = points + "pts";
        }

        return alert;
    }
}
