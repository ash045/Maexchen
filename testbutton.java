import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class MyFrame extends JFrame implements ActionListener{
	

	JButton button;
	JButton button2;
	JButton button3;
	MyFrame(){
		
		button = new JButton("Select Gamemode");
		button.setBounds(125, 100, 500, 50);
		
		button2 = new JButton("Limit of Penalty Points");
		button2.setBounds(275, 200, 200, 50);
		
		button3 = new JButton("Limit of Rounds");
		button3.setBounds(275, 300, 200, 50);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1000,1000);
		this.setVisible(true);
		this.add(button);
		this.add(button2);
		this.add(button3);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			
		}
	}
}
