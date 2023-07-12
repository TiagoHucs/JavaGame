package waves;

import entities.Enemy;
import game.GameComponent;
import ia.BehaviorIA;

import java.util.LinkedList;
import java.util.List;

public abstract class WaveLayout {
    public abstract String[] getLayout();

    public char getEnemyType(String c) {
        return c.charAt(0);
    }

    public List<Enemy> layoutToEnemy(int offSetX, int offSetY, BehaviorIA enemyIA, GameComponent gameComponent) {

        final String[] layout = getLayout();
        final char enemyType = getEnemyType("0");

        List<Enemy> enemyList = new LinkedList<Enemy>();

        Enemy p = new Enemy(null);

        int py = offSetY;

        for (String row : layout) {

            int px = offSetX;
            px -= p.getLargura() + (row.length() / 2) * p.getLargura();
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
