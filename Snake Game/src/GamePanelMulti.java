import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class GamePanelMulti extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int delay = 250;
	final int Ax[] = new int[GAME_UNITS];
	final int Ay[] = new int[GAME_UNITS];
	final int Bx[] = new int[GAME_UNITS];
	final int By[] = new int[GAME_UNITS];
	int AbodyParts = 4;
	int BbodyParts = 4;
	int AapplesEaten;
	int BapplesEaten;
	int appleX;
	int appleY;
	char Adirection = 'R';
	char Bdirection = 'D';
	boolean running = false;
	Timer timer;
	Random random;
	boolean condition; //to check collisions and true is for red and false is for blue
	
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
			for(int i = 0; i<AbodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.red);
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(Ax[i], Ay[i], UNIT_SIZE, UNIT_SIZE);
				} 
				else {
					g.setColor(new Color(220,20,60));
					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(Ax[i], Ay[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//for snakeB
			for(int i = 0; i<BbodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.blue);
					g.fillRect(Bx[i], By[i], UNIT_SIZE, UNIT_SIZE);
				} 
				else {
					g.setColor(new Color(51,153,255));
					g.fillRect(Bx[i], By[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			
		}else {
			gameOver(g,condition);
		}
		
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void moveA() {
		for(int i = AbodyParts; i>0; i--) {
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
		for(int i = BbodyParts; i>0; i--) {
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
	public void AcheckApple() {
		if((Ax[0] == appleX) && (Ay[0] == appleY)) {
			AbodyParts++;
			AapplesEaten++;
			newApple();
		}
	}
	public void BcheckApple() {
		if((Bx[0] == appleX) && (By[0] == appleY)) {
			BbodyParts++;
			BapplesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		
		//For snake RED
		for( int i=AbodyParts; i>0; i--) {
			//check if head collides with body
			if((Ax[0] == Ax[i]) && (Ay[0] == Ay[i])) {
				running = false;
				condition = false;
				}
			}
		//check if head touches the left border
			if(Ax[0] < 0) {
				running = false;
				condition = false;
			}
		//check if head touches the right border
			if((Ax[0] > SCREEN_WIDTH)) {
				running = false;
				condition = false;
			}
		//check if head touches the top border
			if(Ay[0] < 0) {
				running = false;
				condition = false;
			}
		//check if head touches the bottom border
			if(Ay[0] > SCREEN_HEIGHT) {
				running = false;
				condition = false;
			}
			
		//two snake collide	red win
		for(int i=AbodyParts; i>0; i--) {
			if(Bx[0] == Ax[i] && By[0] == Ay[i]) {
				running = false;
				condition = true;
			}
		}
		
		//two snake collide	red win
		for(int i=BbodyParts; i>0; i--) {
			if(Ax[0] == Bx[i] && Ay[0] == By[i]) {
				running = false;
				condition = false;
			}
		}
				
		//___________________________________________________
		//For snake Blue
		for( int i=BbodyParts; i>0; i--) {
			//check if head collides with body
			if((Bx[0] == Bx[i]) && (By[0] == By[i])) {
				running = false;
				condition = true;
			}
		}
		//check if head touches the left border
		if(Bx[0] < 0) {
			running = false;
			condition = true;
		}
			
		//check if head touches the right border
		if((Bx[0] > SCREEN_WIDTH)) {
			running = false;
			condition = true;
		}
			
		//check if head touches the top border
		if(By[0] < 0) {
			running = false;
			condition = true;
		}
			
		//check if head touches the bottom border
		if(By[0] > SCREEN_HEIGHT) {
			running = false;
			condition = true;
		}		
			
		if(!running) {
			timer.stop();
			}
			
		}
	
	public void gameOver(Graphics g,boolean condition) {
		if(condition) {
			//Score
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+AapplesEaten, 
						(SCREEN_WIDTH - metrics1.stringWidth("Score: "+AapplesEaten))/2, 
						g.getFont().getSize());
			
			//Game Over text
			g.setColor(Color.GREEN);
			g.setFont( new Font("Ink Free",Font.BOLD, 50));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("CONGRATULATIONS!", 
						(SCREEN_WIDTH - metrics2.stringWidth("CONGRATULATIONS"))/2, 
						SCREEN_HEIGHT/2);
		}else {
			//Score
			g.setColor(Color.blue);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+BapplesEaten, 
						(SCREEN_WIDTH - metrics1.stringWidth("Score: "+BapplesEaten))/2, 
						g.getFont().getSize());
			
			//Game Over text
			g.setColor(Color.GREEN);
			g.setFont( new Font("Ink Free",Font.BOLD, 50));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("CONGRATULATIONS!", 
						(SCREEN_WIDTH - metrics2.stringWidth("CONGRATULATIONS"))/2, 
						SCREEN_HEIGHT/2);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			moveA();
			moveB();
			AcheckApple();
			BcheckApple();
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
