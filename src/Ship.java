public class Ship extends Ator{

	public Ship(){
		this.setImg("hero.png");
	}

	public Shot atirar() {
		Shot tiro = new Shot(getX(), getY());
		return tiro;
	}



}
