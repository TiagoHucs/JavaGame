package entities;

import effects.Recoil;
import effects.Shake;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class Ship extends Ator{

	private int energia = 100;

	private Recoil recoil = new Recoil();
	public Ship(){
		this.setImage("/image/ships/spaceShips_003.PNG");
	}
	public Shot atirar() {
		Shot tiro = new Shot(getX()+(getLargura()/2), getY());
		this.energia -= 1;
		this.recoil.setSize(10.0f);
		return tiro;
	}

	public void sofreDano(int dano){
		this.energia -= dano;
	}

	@Override
	public void move() {
		super.move();
		recoil.update(this);
	}

}
