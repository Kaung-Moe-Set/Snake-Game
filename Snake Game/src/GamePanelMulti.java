import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class GamePanelMulti extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int delay = 1000;
	final int Ax[] = new int[GAME_UNITS];
	final int Ay[] = new int[GAME_UNITS];
	final int Bx[] = new int[GAME_UNITS];
	final int By[] = new int[GAME_UNITS];
	int bodyParts = 2;
	int AapplesEaten;
	int BapplesEaten;
	int appleX;
	int appleY;
	char Adirection = 'R';
	char Bdirection = 'L';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanelMulti(){
		
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_WIDTH));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(delay,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {
			//draw grids
			for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			
			//for apple
			g.setColor(Color.green);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			//for snakeA
			for(int i = 0; i<bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.red);
					g.fillRect(Ax[i], Ay[i], UNIT_SIZE, UNIT_SIZE);
				} 
				else {
					g.setColor(new Color(220,20,60));
					g.fillRect(Ax[i], Ay[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//for snakeB
			for(int i = 0; i<bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.blue);
					g.fillRect(Bx[i]+(UNIT_SIZE*29), By[i]+(UNIT_SIZE*29), UNIT_SIZE, UNIT_SIZE);
				} 
				else {
					g.setColor(new Color(51,153,255));
					g.fillRect(Bx[i]+(UNIT_SIZE*29), By[i]+(UNIT_SIZE*29), UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			
		}else {
			gameOver(g);
		}
		
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void moveA() {
		for(int i = bodyParts; i>0; i--) {
			Ax[i] = Ax[i - 1];
			Ay[i] = Ay[i - 1];
		}
		
		switch(Adirection) {
		case 'U':
			Ay[0] = Ay[0] - UNIT_SIZE;
			break;
		case 'D':
			Ay[0] = Ay[0] + UNIT_SIZE;
			break;
		case 'L':
			Ax[0] = Ax[0] - UNIT_SIZE;
			break;
		case 'R':
			Ax[0] = Ax[0] + UNIT_SIZE;
			break;
		}
	}
	public void moveB() {
		for(int i = bodyParts; i>0; i--) {
			Bx[i] = Bx[i - 1];
			By[i] = By[i - 1];
		}
		
		switch(Bdirection) {
		case 'U':
			By[0] = By[0] - UNIT_SIZE;
			break;
		case 'D':
			By[0] = By[0] + UNIT_SIZE;
			break;
		case 'L':
			Bx[0] = Bx[0] - UNIT_SIZE;
			break;
		case 'R':
			Bx[0] = Bx[0] + UNIT_SIZE;
			break;
		}
	}
	public void checkApple() {
		if((Ax[0] == appleX) && (Ay[0] == appleY)) {
			bodyParts++;
			AapplesEaten++;
			newApple();
		}else if((Bx[0] == appleX) && (By[0] == appleY)) {
			bodyParts++;
			BapplesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		
	}
	public void gameOver(Graphics g) {
		if(AapplesEaten > BapplesEaten ) {
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+AapplesEaten, 
						(SCREEN_WIDTH - metrics1.stringWidth("Score: "+AapplesEaten))/2, 
						g.getFont().getSize());
			
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("Game Over", 
								(SCREEN_WIDTH - metrics2.stringWidth("RED WIN!"))/2, 
								SCREEN_HEIGHT/2);
			
		}else if(BapplesEaten > AapplesEaten) {
			g.setColor(Color.blue);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+BapplesEaten, 
						(SCREEN_WIDTH - metrics1.stringWidth("Score: "+BapplesEaten))/2, 
						g.getFont().getSize());
			
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("Game Over", 
								(SCREEN_WIDTH - metrics2.stringWidth("BLUE WIN!"))/2, 
								SCREEN_HEIGHT/2);
		}else {
			//Tie condition
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 75));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("Game Over", 
								(SCREEN_WIDTH - metrics2.stringWidth("Tie"))/2, 
								SCREEN_HEIGHT/2);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			moveA();
			moveB();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class myKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(Adirection != 'R') {
					Adirection = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(Adirection != 'L') {
					Adirection = 'R';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(Adirection != 'U') {
					Adirection = 'D';
				}
				break;
			case KeyEvent.VK_UP:
				if(Adirection != 'D') {
					Adirection = 'U';
				}
				break;
			case KeyEvent.VK_A:
				if(Bdirection != 'R') {
					Bdirection = 'L';
				}
				break;
			case KeyEvent.VK_D:
				if(Bdirection != 'L') {
					Bdirection = 'R';
				}
				break;
			case KeyEvent.VK_S:
				if(Bdirection != 'U') {
					Bdirection = 'D';
				}
				break;
			case KeyEvent.VK_W:
				if(Bdirection != 'D') {
					Bdirection = 'U';
				}
				break;
			}
		}
	}
	
}
