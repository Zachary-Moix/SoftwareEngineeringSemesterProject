package GUI;

import java.awt.*;

import javax.swing.*; 

public class LobbyScreen extends JFrame
{
	private JButton join, balance; 
	public LobbyScreen()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel grid = new JPanel(); 
		
		grid.setLayout(new GridLayout(2,1));
		
		JPanel p = new JPanel();
		join = new JButton("Join Game");
		p.add(join);
		
		JPanel b = new JPanel();
		balance = new JButton("Check Balance"); 
		//balance.setPreferredSize(confirm.getPreferredSize());
		b.add(balance);
		
		
		grid.add(p);
		grid.add(b);

		
		this.add(grid, BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args)
	{
		new LobbyScreen(); 
	}
}
