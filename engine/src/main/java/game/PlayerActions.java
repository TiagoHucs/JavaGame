package game;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PlayerActions {

    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String FIRE = "fire";

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean fire = false;

    private Map<String, List<Integer>> buttonMapping;

    public PlayerActions() {
        this.buttonMapping = new HashMap<>(5);
        configureButtons(LEFT, KeyEvent.VK_LEFT, KeyEvent.VK_A);
        configureButtons(RIGHT, KeyEvent.VK_RIGHT, KeyEvent.VK_D);
        configureButtons(UP, KeyEvent.VK_UP, KeyEvent.VK_W);
        configureButtons(DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_S);
        configureButtons(FIRE, KeyEvent.VK_SPACE);
    }

    public void configureButtons(String action, Integer ...keys) {
        this.buttonMapping.put(action, Arrays.asList(keys));
    }

    public void reset() {
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.fire = false;
    }

    private void changeValue(KeyEvent e, boolean value) {

        if (buttonMapping.get(UP).contains(e.getKeyCode())) {
            up = value;

        } else if (buttonMapping.get(DOWN).contains(e.getKeyCode())) {
            down = value;

        } else if (buttonMapping.get(LEFT).contains(e.getKeyCode())) {
            left = value;

        } else if (buttonMapping.get(RIGHT).contains(e.getKeyCode())) {
            right = value;

        } else if (buttonMapping.get(FIRE).contains(e.getKeyCode())) {
            fire = value;
        }

    }

    public void keyPressed(KeyEvent e) {
        changeValue(e, true);
    }

    public void keyReleased(KeyEvent e) {
        changeValue(e, false);
    }
}
