import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GameScreen extends JFrame implements KeyListener{
	
    Config cfg = new Config();
    GameLogic game = new GameLogic();

	public GameScreen(){

		this.addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
		this.setLocationRelativeTo(null);
		//this.setUndecorated(true);
		this.setBackground(Color.BLACK);
		this.add(game);
		this.setVisible(true);
	}
    
	@Override
	public void keyTyped(KeyEvent e) {
		//game.obterAcao(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}

}