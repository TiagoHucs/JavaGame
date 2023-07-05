package game;

import entities.Enemy;
import entities.Ship;
import entities.Shot;

public class Colisor{

	public boolean detectaColisao(Ship a, Enemy b){

        int myleft = a.getX();
        int myright = a.getX() + a.getLargura();
        int mytop = a.getY();
        int mybottom = a.getY() + a.getAltura();

        int otherleft = b.getX();
        int otherright = b.getX() + b.getLargura();
        int othertop = b.getY();
        int otherbottom = b.getY() + b.getAltura();

        if ((mybottom < othertop) ||
               (mytop > otherbottom) ||
               (myright < otherleft) ||
               (myleft > otherright)) {
           return false;
        }else{
        	return true;
        }
	}

	public boolean detectaColisao(Shot a, Enemy b){

        int myleft = a.getX();
        int myright = a.getX() + a.getLargura();
        int mytop = a.getY();
        int mybottom = a.getY() + a.getAltura();

        int otherleft = b.getX();
        int otherright = b.getX() + b.getLargura();
        int othertop = b.getY();
        int otherbottom = b.getY() + b.getAltura();

        if ((mybottom < othertop) ||
               (mytop > otherbottom) ||
               (myright < otherleft) ||
               (myleft > otherright)) {
           return false;
        }else{
        	return true;
        }
	}
}