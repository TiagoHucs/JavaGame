package entities;

import utilities.Config;

public class Shot extends Ator{

	private Config cfg = new Config();
	
	public Shot (int x,int y){
		this.setX(x);
		this.setY(y);
		this.setLargura(cfg.getResolution()/200);
		this.setAltura(this.getLargura()*10);
		this.setVelocidadeY(cfg.getResolution()/(30*-1));
	}
	
}
