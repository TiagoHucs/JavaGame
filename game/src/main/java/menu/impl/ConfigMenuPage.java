package menu.impl;

import game.GameComponent;
import menu.AbstractMenuOption;
import menu.AbstractMenuPage;
import menu.MainMenu;

import java.util.Arrays;

public class ConfigMenuPage extends AbstractMenuPage {

    public static final String OPT_GRAPHICS = "GRAPHICS";
    private static final String OPT_SOUND = "SOUND";
    private static final String OPT_CONTROLLER = "CONTROLLER";

    public ConfigMenuPage(String title,GameComponent gameComponent, MainMenu mainMenu) {
        super(title);

        setOptions(Arrays.asList(
                new AbstractMenuOption(OPT_GRAPHICS) {
                    @Override
                    public void execute() {
                        System.out.println(OPT_GRAPHICS);
                    }
                },
                new AbstractMenuOption(OPT_SOUND) {
                    @Override
                    public void execute() {
                       mainMenu.setMenuPage(MainMenu.KEY_SOUND_MENU);
                    }
                },
                new AbstractMenuOption(OPT_CONTROLLER) {
                    @Override
                    public void execute() {
                        System.out.println(OPT_CONTROLLER);
                    }
                },
                new AbstractMenuOption(OPT_BACK) {
                    @Override
                    public void execute() {
                        mainMenu.setMenuPage(MainMenu.KEY_GAME_MENU);
                    }
                }
        ));
    }


}
