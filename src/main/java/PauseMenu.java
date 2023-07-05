import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseMenu {

    private Config cfg;
    private String[] options = {"RETOMAR PARTIDA","SAIR"};
    private int selectedOption = 0;
    private int width;
    private int height;

    public PauseMenu(Config cfg) {
        this.cfg = cfg;
        this.width = cfg.getLarguraTela()/3;
        this.height = cfg.getAlturaTela()/3;

    }

    public void paintMenu(Graphics g){

        g.setColor(Color.BLACK);
        g.fillRect(width, height, width, height);

        g.setColor(Color.RED);
        g.drawRect(width, height, width, height);

        for(int i = 0; i < options.length; i++) {
            g.setColor(i == selectedOption ? Color.YELLOW : Color.WHITE);
            g.drawString(options[i] , width + 10, height + ((i+1)*20));
        }
    }


    public Integer control(KeyEvent e){

        if (e.getKeyCode() == KeyEvent.VK_UP) {
           if(selectedOption>0){
               selectedOption--;
               playSound("audio/tap.wav");
           }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(selectedOption < options.length-1){
                selectedOption++;
                playSound("audio/tap.wav");
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


    private void playSound(String filename) {
        if (!cfg.isMuted()) {
            try {
                Clip sound = ResourceManager.get().getAudio(filename);
                sound.start();
            } catch (Exception ex) {
                System.err.println("Erro ao tocar o som = " + filename);
                ex.printStackTrace();
            }
        }
    }
}
