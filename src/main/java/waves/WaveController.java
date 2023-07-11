package waves;

import game.GameComponent;
import game.PlayerState;
import utilities.ResourceManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WaveController {
    private float timeToStart;
    private float timeToClean;
    private WaveStatics currentWave;
    private List<WaveStatics> statics;
    private Font font;

    public void init() {
        this.statics = new ArrayList<WaveStatics>();
        this.font = ResourceManager.get().getFont();
        this.nextWave();
    }

    public void resetTimers() {
        this.timeToStart = 3.0f;
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

        float delta = 1.0f / GameComponent.FPS_SET;

        this.timeToClean += delta;
        this.timeToStart = Math.max(0, timeToStart - delta);
    }

    public void draw(Graphics graphics, GameComponent gameComponent) {

        if (timeToStart > 0) {

            int width = 200;
            int height = 50;

            // Centro da Tela - Centro do text-box
            int px = (gameComponent.getWidth() / 2) - (width / 2);
            int py = (gameComponent.getHeight() / 2) - (height / 2);

            graphics.setColor(Color.WHITE);
            graphics.setFont(font);
            graphics.drawString("Wave " + currentWave.getNumber(), px, py);

            int timeToInt = (int) timeToStart + 1;
            graphics.drawString("Starts in " + timeToInt,
                    px - font.getSize(),
                    py + height);
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
    
    public final WaveLayout getWaveLayout() {

        switch (currentWave.getNumber()) {
            case 1: return new LayoutWave01();
            case 2: return new LayoutWave02();
            case 3: return new LayoutWave03();
            case 4: return new LayoutWave04();
        }

        return new LayoutWave01();
    }
    public final List<WaveStatics> getStatics() {
        return statics;
    }
}
