package waves;

import entities.Enemy;
import game.GameComponent;
import ia.BehaviorIA;

import java.util.LinkedHashSet;
import java.util.Set;

public class LayoutWave01 {

    public String[] getLayout() {
        return new String[] {
                "0000000000",
                "0000000000",
                "0000000000",
                "0000000000"
        };
    }

    public Set<Enemy> layoutToEnemy(int offSetX, int offSetY, BehaviorIA enemyIA, GameComponent gameComponent) {

        int py = offSetY;

        Set<Enemy> enemyList = new LinkedHashSet<>(10*4);

        Enemy p = new Enemy(null);

        for (String row : getLayout()) {

            int px = offSetX;
            py += p.getAltura();

            for (int i = 0; i < row.length(); i++) {
                switch (row.charAt(i)) {
                    case '0':
                        p = new Enemy(enemyIA);
                        px += p.getLargura();
                        p.setY(py);
                        p.setX(px);
                        enemyIA.setSpeed(p, gameComponent);
                        enemyList.add(p);
                        break;
                }
            }
        }

        return enemyList;
    }
}
