package waves;

import entities.Enemy;
import game.GameComponent;
import ia.BehaviorIA;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class WaveLayout {
    public abstract String[] getLayout();

    public char getEnemyType(String c) {
        return c.charAt(0);
    }

    public Set<Enemy> layoutToEnemy(int offSetX, int offSetY, BehaviorIA enemyIA, GameComponent gameComponent) {

        int py = offSetY;

        Set<Enemy> enemyList = new LinkedHashSet<>(10 * 4);

        Enemy p = new Enemy(null);

        char enemyType = getEnemyType("0");

        for (String row : getLayout()) {

            int px = offSetX;
            py += p.getAltura();

            for (int i = 0; i < row.length(); i++) {

                px += p.getLargura();

                if (enemyType == row.charAt(i)) {
                    p = new Enemy(enemyIA);
                    p.setY(py);
                    p.setX(px);
                    enemyIA.setSpeed(p, gameComponent);
                    enemyList.add(p);
                }
            }
        }

        return enemyList;
    }
}
