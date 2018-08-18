public class Ship extends Ator{

	private int energia = 100;

	public Ship(){
		this.setImg("hero.png");
	}

	public Shot atirar() {
		Shot tiro = new Shot(getX()+(getLargura()/2), getY());
		this.energia = this.energia-1; 
		return tiro;
	}

	public int getEnergia(){
		return this.energia;
	}

}
