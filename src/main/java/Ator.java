import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class Ator {

	private Config cfg = new Config();
    private int altura = cfg.getResolution() / 20;
    private int largura = altura;
    private int x = (cfg.getLarguraTela() / 2) + (largura / 2);
    private int y = (cfg.getAlturaTela() / 2) - (altura / 2);
    private int velocidadeX = 0;
    private int velocidadeY = 0;

    private BufferedImage image = null;

    public void move() {
        this.x = this.x + this.velocidadeX;
        this.y = this.y + this.velocidadeY;
    }

    public void setImage(String filename) {
        try {
            image = ResourceManager.get().getImage(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseXVelocity(){
        this.velocidadeX = velocidadeX >= 20 ? velocidadeX : velocidadeX+1;
    }

    public void decreaseXVelocity(){
        this.velocidadeX = velocidadeX <= -20 ? velocidadeX : velocidadeX-1;
    }

    public void slowDown(){
        if(this.velocidadeX > 0){
            this.velocidadeX--;
        } else if (this.velocidadeX < 0){
            this.velocidadeX++;
        }
    }
}
