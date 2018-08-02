import java.awt.Image;
import java.awt.Toolkit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ship {

	private Config cfg = new Config();
	private int altura = cfg.getResolution()/20;
	private int largura = altura;
	private int x = (cfg.getLarguraTela()/2)+(largura/2);
	private int y = cfg.getAlturaTela()-(altura*2);
	private int velocidadeX = 0;
	private int velocidadeY = 0;
	//private Image img = Toolkit.getDefaultToolkit().getImage("src\\hero.png");
	private Image img = Toolkit.getDefaultToolkit().getImage("hero.png");

	public void mover() {
		this.setX(this.getX() + this.getVelocidadeX());
		this.setY(this.getY() + this.getVelocidadeY());
	}

	public Shot atirar() {
		Shot tiro = new Shot(getX(), getY());
		return tiro;
	}

}
