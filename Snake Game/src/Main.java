import javax.swing.*;
import java.awt.event.*;

public class Main {

	public static void main(String[] args) {

		new Options();
	}
	
}

class Options implements ActionListener {
	
	JFrame frame = new JFrame();
	JButton single = new JButton("Single Player");
	JButton multi = new JButton("Multi Player");
	
	Options() {
		single.setBounds(30, 100, 150, 40);
		single.setFocusable(false);
		single.addActionListener(this);
		
		multi.setBounds(200, 100, 150, 40);
		multi.setFocusable(false);
		multi.addActionListener(this);

		frame.add(single);
		frame.add(multi);
		frame.setSize(420,300);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == single) {
			frame.dispose();
			new GameFrame(false);
		}else {
			frame.dispose();
			new GameFrame(true);
		}
		
	}
}
