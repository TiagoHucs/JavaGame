package game;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;

@Getter
@Setter
public class PlayerActions {

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean fire = false;

    public void reset() {
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.fire = false;
    }

    private void changeValue(KeyEvent e, boolean value) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = value;
                break;
            case KeyEvent.VK_DOWN:
                down = value;
                break;
            case KeyEvent.VK_LEFT:
                left = value;
                break;
            case KeyEvent.VK_RIGHT:
                right = value;
                break;
            case KeyEvent.VK_SPACE:
                fire = value;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        changeValue(e, true);
    }

    public void keyReleased(KeyEvent e) {
        changeValue(e, false);
    }
}
