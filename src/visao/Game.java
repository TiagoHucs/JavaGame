package visao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JComponent;

import modelo.Config;
import modelo.Ship;
import modelo.Shot;
import modelo.Space;

class Game extends JComponent {

	private Ship heroi = new Ship();
	Image img1 = Toolkit.getDefaultToolkit().getImage("src\\disk.png");
	ArrayList<Shot> listaTiros = new ArrayList<Shot>();
	Space espaco = new Space();
	Config cfg = new Config();

	public Game() {

		Thread animationThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					update();
					repaint();
					try {
						Thread.sleep(10);
					} catch (Exception ex) {
					}
				}
			}

		});

		animationThread.start();

	}

	public void paintComponent(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0,0,cfg.getLarguraTela(),cfg.getAlturaTela());
		
		g.setColor(heroi.getCor());
		g.drawImage(img1, heroi.getX(), heroi.getY(), 50, 50, this);
		
		g.setColor(Color.YELLOW);
		for (int i = 0; i < listaTiros.size(); i++) {
			g.fillRect(listaTiros.get(i).getX(), listaTiros.get(i).getY(), 5,
					30);

		}
		g.setColor(Color.WHITE);
		for (int i = 0; i < espaco.getEstrelas().size(); i++) {
			int dim = espaco.getEstrelas().get(i).getDim();
			g.fillOval(espaco.getEstrelas().get(i).getX(), espaco.getEstrelas()
					.get(i).getY(), dim, dim);
			

		}

	}

	private void update() {
		heroi.mover();
		for (int i = 0; i < listaTiros.size(); i++) {
			listaTiros.get(i).move();
			if (listaTiros.get(i).getY() < -10) {
				listaTiros.remove(i);
			}
		}
		for (int i = 0; i < espaco.getEstrelas().size(); i++) {
			espaco.getEstrelas().get(i).move();
		}

	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			heroi.setVelocidadeY(-5);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			heroi.setVelocidadeY(5);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			heroi.setVelocidadeX(-5);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			heroi.setVelocidadeX(5);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			listaTiros.add(heroi.atirar());
		}

	}

	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			heroi.setVelocidadeY(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			heroi.setVelocidadeY(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			heroi.setVelocidadeX(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			heroi.setVelocidadeX(0);
		}

	}

}
