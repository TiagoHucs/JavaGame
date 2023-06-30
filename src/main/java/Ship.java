public class Ship extends Ator{

	private int energia = 100;

	public Ship(){
		this.setImg("hero.png");
	}

	public Shot atirar() {
		playSound("bling.wav");
		Shot tiro = new Shot(getX()+(getLargura()/2), getY());
		this.energia = this.energia-1; 
		return tiro;
	}

	public int getEnergia(){
		return this.energia;
	}

	public void sofreDano(int d){
		playSound("im-hit.wav");
		this.energia = this.energia-d; 
	}

}
