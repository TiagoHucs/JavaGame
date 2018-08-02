import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

public class Star {
	
	Config cfg = new Config();
	private int x;
	private int y;
	private int velocidadeY;
	Random r = new Random();
	private int dim;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = cfg.getLarguraTela();
	int height = cfg.getAlturaTela();
	
	public Star(){
		this.velocidadeY = (r.nextInt(2)+1);
		this.dim = velocidadeY+1;
		this.x = r.nextInt(width);
		this.y = r.nextInt(height);

	}

	public int getVelocidadeY() {
		return velocidadeY;
	}

	public void setVelocidadeY(int velocidadeY) {
		this.velocidadeY = velocidadeY;
	}

	public int getDim() {
		return dim;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void move(){
		this.y = this.y + this.velocidadeY;
		if(this.y>height){
			this.x = r.nextInt(width);
			this.y = 0;
		}
	}
	
	

}
