package invaders;

import effects.StarFieldEffect;
import game.GameComponent;
import game.GameState;
import game.SinglePlayerGameLogic;
import game.SoundManager;
import invaders.menu.InvadersMenu;

public class InvadersGameComponent extends GameComponent {

    @Override
    public void init() {
        this.menu = new InvadersMenu(this);
        this.soundManager = new SoundManager();
        this.soundManager.loadSounds("/audio");
        this.soundManager.setGlobalVolume(getCfg().getGlobalVolume());
        this.gameState = new GameState();
        this.gameState.state = GameState.State.MENU;
        this.currentGameLogic = new SinglePlayerGameLogic(this);
        //this.starFieldEffect = new StarFieldEffect(this, true);
    }
}
