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
            case KeyEvent.VK_W:
                up = value;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                down = value;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = value;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
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
