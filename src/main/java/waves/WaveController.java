package waves;

import game.GameComponent;
import game.PlayerState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WaveController {
    private float timeToStart;
    private float timeToClean;
    private WaveStatics currentWave;
    private List<WaveStatics> statics;
    private Font font = new Font("Arial", Font.BOLD, 40);

    public void init() {
        this.statics = new ArrayList<WaveStatics>();
        this.nextWave();
    }

    public void resetTimers() {
        this.timeToStart = 100.0f;
        this.timeToClean = 0.0f;
    }

    public void updateCurrentWaveStatics(PlayerState playerState) {
        currentWave.addPoints(playerState.getScore());
        currentWave.setTimeToClean(timeToClean);
    }

    public void nextWave() {

        this.currentWave = new WaveStatics();
        this.currentWave.setNumber(1 + this.statics.size());

        this.statics.add(currentWave);
        this.resetTimers();
    }

    public void updateStatics() {
        this.timeToClean++;
        this.timeToStart = Math.max(0, timeToStart - 1);
    }

    public void draw(Graphics graphics, GameComponent gameComponent) {

        if (timeToStart > 0) {

            int width = 200;
            int height = 50;

            int px = (gameComponent.getWidth() / 2) - (width / 2);
            int py = (gameComponent.getHeight() / 2) - (height / 2);

            graphics.setColor(Color.WHITE);
            graphics.setFont(font);
            graphics.drawString("Wave " + currentWave.getNumber(), px, py);
            graphics.drawString("Starts in " + timeToStart, px, py + height);
        }

    }

    public boolean isCanCreateEnemyWave() {
        return timeToStart == 0 && !currentWave.isFinished();
    }

    public boolean goToNextWave() {
        return timeToStart == 0 && currentWave.isFinished();
    }

    public void finishCurrentWave() {
        if (timeToStart == 0) {
            this.currentWave.setFinished(true);
        }
    }

}
