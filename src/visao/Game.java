package visao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;

import modelo.Config;
import modelo.Enemy;
import modelo.Ship;
import modelo.Shot;
import modelo.Space;

class Game extends JComponent {
	
	Config cfg = new Config();
	
	private Ship nave = new Ship();
	private ArrayList<Shot> listaTiros = new ArrayList<Shot>();
	private Space espaco = new Space();
	private int speedDefault = cfg.getResolution()/200;
	private Random r = new Random();
	private Enemy inimigo1 = new Enemy();
		
	public Game() {
		inimigo1.setY(10);
		inimigo1.setX(r.nextInt(cfg.getResolution()));

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
		
		g.setColor(Color.WHITE);
		g.drawImage(nave.getImg(), nave.getX(), nave.getY(),nave.getLargura(), nave.getAltura(), this);
		
		g.setColor(Color.RED);
		g.drawImage(inimigo1.getImg(),inimigo1.getX(),inimigo1.getY(),inimigo1.getLargura(),inimigo1.getLargura(),this);
		
		g.setColor(Color.YELLOW);
		for (Shot tiro : listaTiros) {
			g.fillRect(tiro.getX(),tiro.getY(),tiro.getLargura(),tiro.getAltura());
		}
		
		
		
		for (int i = 0; i < listaTiros.size(); i++) {
			g.fillRect(listaTiros.get(i).getX(), listaTiros.get(i).getY(), listaTiros.get(i).getLargura(),
					listaTiros.get(i).getAltura());

		}
		g.setColor(Color.gray);
		for (int i = 0; i < espaco.getEstrelas().size(); i++) {
			int dim = espaco.getEstrelas().get(i).getDim();
			g.fillOval(espaco.getEstrelas().get(i).getX(), espaco.getEstrelas()
					.get(i).getY(), dim, dim);
			

		}

	}

	private void update() {
		nave.mover();
		inimigo1.mover();
		for (int i = 0; i < listaTiros.size(); i++) {
			listaTiros.get(i).move();
			if (listaTiros.get(i).getY() < -10) {
				listaTiros.remove(i);
			}
		}
		for (int i = 0; i < espaco.getEstrelas().size(); i++) {
			espaco.getEstrelas().get(i).move();
		}
		if(inimigo1.getY()>cfg.getAlturaTela()){
			inimigo1.setY(-5);
			inimigo1.setX(r.nextInt(cfg.getResolution()));
		}
		
	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			nave.setVelocidadeY(-speedDefault);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			nave.setVelocidadeY(speedDefault);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			nave.setVelocidadeX(-speedDefault);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			nave.setVelocidadeX(speedDefault);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			listaTiros.add(nave.atirar());
		}

	}

	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			nave.setVelocidadeY(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			nave.setVelocidadeY(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			nave.setVelocidadeX(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			nave.setVelocidadeX(0);
		}

	}

}
