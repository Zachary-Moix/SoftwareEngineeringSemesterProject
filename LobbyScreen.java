package main;

import java.awt.*;

import javax.swing.*; 

public class LobbyScreen extends JPanel
{
	private JButton join, balance; 
	public LobbyScreen(CardLayout cl, JPanel container, Client client, JFrame frame)
	{
		LobbyControl lc = new LobbyControl(cl,container,client,frame);
		
		JPanel grid = new JPanel(); 
		
		grid.setLayout(new GridLayout(2,1));
		
		JPanel p = new JPanel();
		join = new JButton("Join Game");
		join.addActionListener(lc);
		p.add(join);
		
		JPanel b = new JPanel();
		balance = new JButton("Check Balance"); 
		balance.addActionListener(lc);
		//balance.setPreferredSize(confirm.getPreferredSize());
		b.add(balance);
		
		
		grid.add(p);
		grid.add(b);

		
		this.add(grid, BorderLayout.CENTER);
		this.setVisible(true);
	}
}
