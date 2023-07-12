package waves;

import entities.Enemy;
import game.GameComponent;
import ia.BehaviorIA;

import java.awt.geom.Point2D;
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
            px -= p.getSize().x + (row.length() / 2) * p.getSize().x;
            py += p.getSize().y;

            for (int i = 0; i < row.length(); i++) {

                px += p.getSize().x;

                if (enemyType == row.charAt(i)) {
                    p = new Enemy(enemyIA);
                    p.setPosition(new Point2D.Float(px, py));
                    enemyIA.setSpeed(p, gameComponent);
                    enemyList.add(p);
                }
            }
        }

        return enemyList;
    }
}
