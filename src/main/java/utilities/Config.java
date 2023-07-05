package utilities;

import java.awt.*;

public class Config {

	public Config(){};
	
	//private int resolution = 900;
	//private int larguraTela = resolution;
	//private int alturaTela = ((resolution/100)*75);
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int larguraTela = (int) screenSize.getWidth();
	private int alturaTela = (int) screenSize.getHeight();
	private int resolution = (int) screenSize.getWidth();

	private boolean muted = true;

	public int getResolution(){
		return this.resolution;
	}
	public int getLarguraTela(){
		return this.larguraTela;
	}
	public int getAlturaTela(){
		return this.alturaTela;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}
}
