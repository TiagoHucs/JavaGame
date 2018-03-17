package modelo;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Config {
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int larguraTela = (int) screenSize.getWidth();
	private  int alturaTela = (int) screenSize.getHeight();
	
	public int getLarguraTela() {
		return larguraTela;
	}
	public int getAlturaTela() {
		return alturaTela;
	}
	
	

}
