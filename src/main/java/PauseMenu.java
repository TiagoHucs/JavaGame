import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseMenu {

    String[] options = {"RETOMAR PARTIDA","SAIR"};
    int selectedOption = 0;

    public void paintMenu(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(100, 100, 500, 300);

        g.setColor(Color.GRAY);
        g.drawRect(100, 100, 500, 300);

        int y = 160;
        for(int i = 0; i < options.length; i++) {
            g.setColor(i == selectedOption ? Color.YELLOW : Color.WHITE);
            g.drawString(options[i] , 160, y);
            y = y+20;
        }
    }


    public Integer control(KeyEvent e){

        if (e.getKeyCode() == KeyEvent.VK_UP) {
           if(selectedOption>0){
               selectedOption--;
           }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(selectedOption < options.length-1){
                selectedOption++;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(selectedOption == 0){
                return selectedOption;
            }
            if(selectedOption == 1){
                System.exit(0);
            }
        }

        return null;

    }


}
