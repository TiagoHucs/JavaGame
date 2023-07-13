package menu.impl;

import game.GameComponent;
import menu.AbstractMenuOption;
import menu.AbstractMenuOptionLevel;
import menu.AbstractMenuPage;
import menu.MainMenu;

import java.util.Arrays;

public class SoundMenuPage extends AbstractMenuPage {

    public static final String OPT_MUTED = "MUTED: TRUE";
    private static final String OPT_SOUND_LEVEL = "LEVEL VOLUME: 100%";

    public SoundMenuPage(String title, GameComponent gameComponent, MainMenu mainMenu) {
        super(title);

        setOptions(Arrays.asList(
                new AbstractMenuOptionLevel(OPT_MUTED) {
                    @Override
                    public void execute() {
                        System.out.println(OPT_MUTED);
                    }

                    @Override
                    public void increase() {
                        System.out.println("Increasing "+ OPT_MUTED);
                    }

                    @Override
                    public void decrease() {
                        System.out.println("Decreasing "+ OPT_MUTED);
                    }
                },
                new AbstractMenuOption(OPT_SOUND_LEVEL) {
                    @Override
                    public void execute() {
                       System.out.println(OPT_SOUND_LEVEL);
                    }
                },
                new AbstractMenuOption(OPT_BACK) {
                    @Override
                    public void execute() {
                        mainMenu.setMenuPage(MainMenu.KEY_CONFIGURATIONS_MENU);
                    }
                }
        ));
    }


}
