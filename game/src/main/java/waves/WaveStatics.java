package waves;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaveStatics {
    private int number;
    private int points;
    private float timeToClean;
    private boolean finished;
    public void addPoints(int points) {
        this.points += points;
    }
}
