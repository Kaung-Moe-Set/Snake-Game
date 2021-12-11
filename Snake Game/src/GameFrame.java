import javax.swing.JFrame;

public class GameFrame extends JFrame {

	GameFrame(boolean check) {
	
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}
