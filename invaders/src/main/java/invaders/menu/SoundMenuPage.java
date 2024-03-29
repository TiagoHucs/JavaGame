package invaders.menu;

import game.GameComponent;
import menu.AbstractMenuOption;
import menu.AbstractMenuOptionLevel;
import menu.AbstractMenuPage;
import menu.AbstractMenu;

import java.util.Arrays;

public class SoundMenuPage extends AbstractMenuPage {

    public static final String KEY = "KEY_SOUND_MENU";

    public static final String OPT_MUTED = "MUTED";
    private static final String OPT_SOUND_LEVEL = "LEVEL VOLUME";

    public SoundMenuPage(GameComponent gameComponent, AbstractMenu menu) {
        super( gameComponent);

        setOptions(Arrays.asList(
                new AbstractMenuOptionLevel(OPT_MUTED) {
                    @Override
                    public void execute() {
                        System.out.println(OPT_MUTED);
                    }

                    private void adjustMute(boolean mute) {
                        gameComponent.getCfg().setMuted(mute);
                        gameComponent.getSoundManager().toogleMute(mute);
                    }

                    @Override
                    public void increase() {
                        adjustMute(true);
                    }

                    @Override
                    public void decrease() {
                        adjustMute(false);
                    }

                    @Override
                    public String getTitle() {
                        return super.getTitle() + ": " + gameComponent.getCfg().isMuted();
                    }
                },
                new AbstractMenuOptionLevel(OPT_SOUND_LEVEL) {
                    @Override
                    public void execute() {
                       System.out.println(OPT_SOUND_LEVEL);
                    }

                    private void adjustVolume(int volume) {
                        gameComponent.getCfg().setSoundVolume(gameComponent.getCfg().getSoundVolume() + volume);
                        gameComponent.getSoundManager().setGlobalVolume(gameComponent.getCfg().getGlobalVolume());
                    }

                    @Override
                    public void increase() {
                        adjustVolume(10);
                    }

                    @Override
                    public void decrease() {
                        adjustVolume(-10);
                    }

                    public String getTitle() {
                        return super.getTitle() + ": " + gameComponent.getCfg().getSoundVolume() + "%";
                    }
                },
                new AbstractMenuOption(OPT_BACK) {
                    @Override
                    public void execute() {
                        menu.setMenuPage(ConfigMenuPage.KEY);
                    }
                }
        ));
    }


    @Override
    public String getKey() {
        return KEY;
    }
}
