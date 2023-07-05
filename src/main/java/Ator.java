import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
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
    private int maxVelocity = 15;

    private BufferedImage image = null;

    public void move() {
        this.x = this.x + this.velocidadeX;
        this.y = this.y + this.velocidadeY;
    }

    public void setImage(String filename) {
        try {
            image = ResourceManager.get().getImage(filename, getLargura(), getAltura());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void increaseXVelocity(){
        this.velocidadeX = velocidadeX >= maxVelocity ? velocidadeX : velocidadeX+1;
    }

    public void decreaseXVelocity(){
        this.velocidadeX = velocidadeX <= -maxVelocity ? velocidadeX : velocidadeX-1;
    }

    public void increaseYVelocity(){
        this.velocidadeY = velocidadeY >= maxVelocity ? velocidadeY : velocidadeY+1;
    }

    public void decreaseYVelocity(){
        this.velocidadeY = velocidadeY <= -maxVelocity ? velocidadeY : velocidadeY-1;
    }

    public void slowDownX(){
        if(this.velocidadeX > 0){
            this.velocidadeX--;
        } else if (this.velocidadeX < 0){
            this.velocidadeX++;
        }
    }

    public void slowDownY(){
        if(this.velocidadeY > 0){
            this.velocidadeY--;
        } else if (this.velocidadeY < 0){
            this.velocidadeY++;
        }
    }
}
