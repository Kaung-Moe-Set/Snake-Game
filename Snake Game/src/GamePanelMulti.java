import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class GamePanelMulti extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int delay = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanelMulti(){
		
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_WIDTH));
		this.setBackground(Color.black);
		this.setFocusable(false);
		startGame();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
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
		
	}
	public void newApple() {
		
	}
	public void move() {
		
	}
	public void checkApple() {
		
	}
	public void checkCollisions() {
		
	}
	public void Gameover() {
		
	}
	
}
