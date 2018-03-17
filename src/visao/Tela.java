package visao;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import modelo.Config;

public abstract class Tela extends JFrame implements KeyListener{
	
	Config cfg = new Config();
	Game game = new Game();
	
	public Tela(){
		this.addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(cfg.getLarguraTela(), cfg.getAlturaTela());
		this.setLocationRelativeTo(null);
		//this.setUndecorated(true);
		this.setBackground(Color.BLACK);
		this.add(game);
		this.setVisible(true);
	}

    public static void main(String[] args) {
        new Tela() {};
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