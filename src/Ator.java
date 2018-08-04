import java.awt.Image;
import java.awt.Toolkit;

public class Ator{

	private Config cfg = new Config();
	private int altura = cfg.getResolution()/20;
	private int largura = altura;
	private int x = (cfg.getLarguraTela()/2)+(largura/2);
	private int y = (cfg.getAlturaTela()/2)-(altura/2);
	private int velocidadeX = 0;
	private int velocidadeY = 0;
	//private Image img = Toolkit.getDefaultToolkit().getImage("src\\hero.png");
	private Image img;

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
		return this.img;
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

	public void setImg(String img){
		this.img = Toolkit.getDefaultToolkit().getImage(img);
	}



}
