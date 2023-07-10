package waves;

import entities.Enemy;
import game.GameComponent;
import ia.BehaviorIA;

import java.util.LinkedHashSet;
import java.util.Set;

public class LayoutWave01 extends WaveLayout {

    public String[] getLayout() {
        return new String[] {
                "0000000000",
                "0000000000",
                "0000000000",
                "0000000000"
        };
    }
}
