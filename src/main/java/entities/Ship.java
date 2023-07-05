package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ship extends Ator{

	private int energia = 100;

	public Ship(){
		this.setImage("/image/ships/spaceShips_003.PNG");
	}

	public Shot atirar() {
		Shot tiro = new Shot(getX()+(getLargura()/2), getY());
		this.energia = this.energia-1; 
		return tiro;
	}

	public void sofreDano(int d){
		this.energia = this.energia-d; 
	}

}
