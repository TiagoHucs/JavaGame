package entities;

import utilities.GameUtil;

public class Enemy extends Ator{
		
	public Enemy(int x, int y){
		this.setImage("/image/meteors/spaceMeteors_00"+
				GameUtil.getRandomNumber(1,4)
				+".PNG");
		this.setVelocidadeY(2);
		this.setX(x);
		this.setY(y);
	}

}