package modelo;

import java.awt.Color;

public class Ship {
	
	private int altura = 50;
	private int largura = 50;
	private int x = 10;
	private int y = 100;
	private int velocidadeX;
	private int velocidadeY;
	private Color cor = Color.GRAY;
	
	
	public int getAltura() {
		return altura;
	}

	public int getLargura() {
		return largura;
	}

	
	
	public Ship(){
		
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

	public Color getCor() {
		return cor;
	}

	public int getVelocidadeX() {
		return velocidadeX;
	}

	public void setVelocidadeX(int velocidadeX) {
		this.velocidadeX = velocidadeX;
	}

	public int getVelocidadeY() {
		return velocidadeY;
	}

	public void setVelocidadeY(int velocidadeY) {
		this.velocidadeY = velocidadeY;
	}
	
	public void mover(){
		this.setX(this.getX()+this.getVelocidadeX());
		this.setY(this.getY()+this.getVelocidadeY());
	}
	
	public Shot atirar(){
		Shot tiro = new Shot(getX(),getY());
		return tiro;
	}
	

}
