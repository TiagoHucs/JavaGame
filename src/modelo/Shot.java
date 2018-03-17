package modelo;

public class Shot {
	
	private int x = 0;
	private int y = 0;
	private int velocidadeY = 50;
	
	public Shot (int x,int y){
		this.x = x+25;
		this.y = y;

		
		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void move(){
		this.y = this.y - this.velocidadeY;
	}
	

}
