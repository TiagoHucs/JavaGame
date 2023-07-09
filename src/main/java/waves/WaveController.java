package waves;

import entities.Ator;
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
        this.timeToStart = 100.0f;
        this.timeToClean = 0.0f;
    }

    public void updateCurrentWaveStatics(PlayerState playerState) {
        if (currentWave != null) {
            currentWave.addPoints(playerState.getScore());
            currentWave.setTimeToClean(timeToClean);
        }
    }

    public void nextWave() {

        this.timeToStart = 100.0f;
        this.timeToClean = 0.0f;

        this.currentWave = new WaveStatics();
        this.currentWave.setNumber(this.statics.size() + 1);

        this.statics.add(currentWave);
    }

    public void updateStatics() {
        this.timeToStart = Math.max(0, timeToStart - 1);
        this.timeToClean++;
    }

    public void draw(Graphics graphics, GameComponent gameComponent) {

        int waveNumber = currentWave == null ? 1 : currentWave.getNumber();
        boolean isNewWave = waveNumber != this.statics.size();

        if (!isWaveStarted() && isNewWave) {

            int width = 200;
            int height = 50;
            int px = (gameComponent.getWidth() / 2) - (width / 2);
            int py = (gameComponent.getHeight() / 2) - (height / 2);

            graphics.setColor(Color.WHITE);
            graphics.setFont(font);
            graphics.drawString("Wave " + waveNumber, px, py);
            graphics.drawString("Starts in " + timeToStart, px, py + height);
        }

    }

    public boolean isWaveStarted() {
        return this.timeToStart == 0;
    }
}
