import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;

public class Game extends JComponent {
	
	Config cfg = new Config();
	
	private Ship nave = new Ship();
	private ArrayList<Shot> listaTiros = new ArrayList<Shot>();
	private ArrayList<Enemy> listaInimigos = new ArrayList<Enemy>();
	private Space espaco = new Space();
	private int speedDefault = cfg.getResolution()/200;
	private Random r = new Random();
	private Enemy novoInimigo = new Enemy();
	private Colisor colisor = new Colisor();
		
	public Game() {

		for (int i = 0; i < 10; i++) {
			novoInimigo = new Enemy();
			novoInimigo.setY(i*-10);// aumentar a distancia entre os inimigos
			novoInimigo.setX(r.nextInt(cfg.getResolution()));

			listaInimigos.add(novoInimigo);
		}

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
		
		for (Enemy i : listaInimigos) {
			g.drawImage(i.getImg(),i.getX(),i.getY(),i.getLargura(),i.getLargura(),this);
		}

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

		nave.move();

		for (int i = 0; i < listaTiros.size(); i++) {
			listaTiros.get(i).move();
		}

		for (int i = 0; i < listaInimigos.size(); i++) {
			listaInimigos.get(i).move();
			if(listaInimigos.get(i).getY() > cfg.getAlturaTela()){
				listaInimigos.get(i).setY(-100);
				listaInimigos.get(i).setX(r.nextInt(cfg.getResolution()));
			}
		}

		for (int e = 0; e < listaInimigos.size(); e++) {
			for (int i = 0; i < listaTiros.size(); i++) {
				if (listaTiros.get(i).getY() < -10) {
					listaTiros.remove(i);
				}else if ( colisor.detectaColisao( listaTiros.get(i),listaInimigos.get(e) ) ){
					listaTiros.remove(i);
					listaInimigos.remove(e);
				}
			}
		}

		for (int i = 0; i < espaco.getEstrelas().size(); i++) {
			espaco.getEstrelas().get(i).move();
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
