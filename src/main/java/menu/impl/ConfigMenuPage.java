package menu.impl;

import game.GameComponent;
import game.GameState;
import menu.AbstractMenuOption;
import menu.AbstractMenuPage;
import menu.MainMenu;
import menu.MenuPage;

import java.util.Arrays;

public class ConfigMenuPage extends AbstractMenuPage {

    public static final String OPT_GRAPHICS = "GRAPHICS";
    private static final String OPT_SOUND = "SOUND";
    private static final String OPT_CONTROLLER = "CONTROLLER";
    private static final String OPT_BACK = "BACK";

    private static final String KEY_BACK_PAGE = "GAME MENU";
    private static final String KEY_SOUND_MENU = "SOUND MENU";

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
                       System.out.println(OPT_SOUND);
                       mainMenu.setMenuPage(KEY_SOUND_MENU);
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
                        System.out.println(KEY_BACK_PAGE);
                        mainMenu.setMenuPage(KEY_BACK_PAGE);
                    }
                }
        ));
    }


}
