package game;

import utilities.Config;
import utilities.ResourceManager;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseMenu {

    public static final String OPT_RETOMAR_PARTIDA = "RETOMAR PARTIDA";
    private static final String OPT_CONFIGURACOES = "CONFIGURAÇÕES";
    private static final String OPT_SAIR_DESKTOP = "SAIR PARA DESKTOP";
    private static final String OPT_SOM = "CONFIGURAÇÕES SOM";
    private static final String OPT_DIFICULDADE = "DIFICULDADE DO JOGO";
    private static final String OPT_CONTROLE = "CONFIGURAÇÕES DE CONTROLE";
    private static final String OPT_VOLTA_PRINCIPAL = "VOLTAR PARA O MENU PRINCIPAL";

    public static final String SND_TAB = "/audio/changing-tab.wav";
    public static final String SND_TIC = "/audio/tap.wav";

    private Config cfg;
    private String[] defaultOptions = {OPT_RETOMAR_PARTIDA,OPT_CONFIGURACOES,OPT_SAIR_DESKTOP};
    private String[] configOptions = {OPT_SOM,OPT_DIFICULDADE,OPT_CONTROLE,OPT_VOLTA_PRINCIPAL};
    private String[] options = defaultOptions;
    private int selectedOption = 0;
    private int width;
    private int height;

    public PauseMenu(Config cfg) {
        this.cfg = cfg;
        this.width = cfg.getLarguraTela()/3;
        this.height = cfg.getAlturaTela()/3;

    }

    public void paintMenu(Graphics g){

        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        g.setColor(Color.BLACK);
        g.fillRect(width, height, width, height);

        g.setColor(Color.RED);
        g.drawRect(width, height, width, height);

        for(int i = 0; i < options.length; i++) {
            g.setColor(i == selectedOption ? Color.YELLOW : Color.WHITE);
            g.drawString(options[i] , width + 10, height + ((i+1)*30));
        }
    }


    public String control(KeyEvent e){

        if (e.getKeyCode() == KeyEvent.VK_UP) {
           if(selectedOption>0){
               selectedOption--;
               playSound(SND_TIC);
           }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(selectedOption < options.length-1){
                selectedOption++;
                playSound(SND_TIC);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            playSound(SND_TAB);
            switch (options[selectedOption]){
                case OPT_RETOMAR_PARTIDA:
                    return OPT_RETOMAR_PARTIDA;
                case OPT_CONFIGURACOES:
                    options = configOptions;
                    selectedOption = 0;
                    break;
                case OPT_VOLTA_PRINCIPAL:
                    options = defaultOptions;
                    selectedOption = 0;
                    break;
                case OPT_SAIR_DESKTOP:
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
