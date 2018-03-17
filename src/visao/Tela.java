package visao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public abstract class Tela extends JFrame implements KeyListener{
	

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	Game game = new Game();
	
	public Tela(){
		this.addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
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