package modelo;

import java.awt.Image;
import java.awt.Toolkit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy {

	private Config cfg = new Config();
	private int altura = cfg.getResolution()/20;
	private int largura = altura;
	private int x = (cfg.getLarguraTela()/2)+(largura/2);
	private int y = cfg.getAlturaTela()-(altura*2);
	private int velocidadeX = 0;
	private int velocidadeY = 2;
	private Image img = Toolkit.getDefaultToolkit().getImage("src\\enemy.png");

	public void mover() {
		this.setX(this.getX() + this.getVelocidadeX());
		this.setY(this.getY() + this.getVelocidadeY());
	}

	public Shot atirar() {
		Shot tiro = new Shot(getX(), getY());
		return tiro;
	}
	
	public void destroi(){
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
