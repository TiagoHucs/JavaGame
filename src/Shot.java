import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shot {
	
	private Config cfg = new Config();
	private int x = 0;
	private int y = 0;
	private int largura = cfg.getResolution()/100;
	private int altura = largura;
	private int velocidadeY = cfg.getResolution()/100;;
	
	public Shot (int x,int y){
		this.x = x  ;
		this.y = y;
	}
	
	public void move(){
		this.y = this.y - this.velocidadeY;
	}
	

}
