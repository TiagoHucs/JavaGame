package waves;

import game.GameComponent;
import game.PlayerState;
import utilities.GameUtil;
import utilities.ResourceManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WaveController {
    private float timeToStart;
    private float timeToClean;
    private WaveStatics currentWave;
    private List<WaveStatics> statics;
    private final Font font = ResourceManager.get().getFont();

    public void init() {
        this.statics = new ArrayList<WaveStatics>();
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

            // Preparando graphics
            graphics.setFont(font);
            graphics.setColor(Color.CYAN);

            // Descobrindo o centro da tela
            Dimension screenCenter = new Dimension (
                    gameComponent.getCfg().getGameWidth() / 2,
                    gameComponent.getCfg().getGameHeight() / 2);

            // Texto 1
            String waveText = "Wave " + String.format("%02d", currentWave.getNumber());

            // TODO: depois achar uma forma melhor de pegar o tamanho da fonte, já que é a mesma para o jogo melhor ter um util para isso
            Dimension centerText = new Dimension(
                    waveText.length() * font.getSize() / 2,
                    waveText.length() * font.getSize() / 2);

            graphics.drawString(waveText, screenCenter.width - centerText.width, screenCenter.height - centerText.height);

            // Texto 2
            int timeToInt = (int) timeToStart + 1;
            String timeToStartText = "Starts in " + String.format("%02d", timeToInt);

            centerText = new Dimension(
                    timeToStartText.length() * font.getSize() / 2,
                    timeToStartText.length() * font.getSize() / 2);

            graphics.drawString(timeToStartText, screenCenter.width - centerText.width, screenCenter.height - centerText.height);
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
    
    public WaveLayout getWaveLayout() {

        WaveLayout waveLayout = createWaveLayout(currentWave.getNumber());

        // Chegou ao fim? pega qualquer uma na sorte ai então...
        if (waveLayout == null) {
            waveLayout = createWaveLayout(GameUtil.getRandomNumber(1, 4));
        }

        return waveLayout;
    }

    private WaveLayout createWaveLayout(int layoutNumber) {

        switch (layoutNumber) {
            case 0:
            case 1: return new LayoutWave01();
            case 2: return new LayoutWave02();
            case 3: return new LayoutWave03();
            case 4: return new LayoutWave04();
        }

        return null;
    }
    public final List<WaveStatics> getStatics() {
        return statics;
    }
}
