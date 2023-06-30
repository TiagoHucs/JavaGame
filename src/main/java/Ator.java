import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ator{

	private static final String RESOURCE_PATH = "src/main/resources/";

	private Config cfg = new Config();
	private int altura = cfg.getResolution()/20;
	private int largura = altura;
	private int x = (cfg.getLarguraTela()/2)+(largura/2);
	private int y = (cfg.getAlturaTela()/2)-(altura/2);
	private int velocidadeX = 0;
	private int velocidadeY = 0;
	private BufferedImage image = null;

	public void move() {
		this.x = this.x + this.velocidadeX;
		this.y = this.y + this.velocidadeY;
	}

	public void destroi(){
		System.gc();
	}

	// getters
	public int getAltura(){
		return this.altura;
	}

	public int getLargura(){
		return this.largura;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public int getVelocidadeX(){
		return this.velocidadeX;
	}

	public int getVelocidadeY(){
		return this.velocidadeY;
	}

	public Image getImg(){
		return this.image;
	}


	//setters
	public void setAltura(int altura){
		this.altura = altura;
	}

	public void setLargura(int largura){
		this.largura = largura;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setVelocidadeX(int velX){
		this.velocidadeX = velX;
	}

	public void setVelocidadeY(int velY){
		this.velocidadeY = velY;
	}

	public void setImg(String imgName){
		try {
			String path = cfg.getRelativePath();
			image = ImageIO.read(new File(path, imgName));
		} catch (
				IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void playSound(String filename) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(RESOURCE_PATH+filename).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch(Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}



}
