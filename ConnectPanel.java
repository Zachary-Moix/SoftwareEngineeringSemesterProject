package main;

import java.awt.*;

import javax.swing.*;

public class ConnectPanel extends JPanel
{
	private JTextField ip, port; 
	private JButton submit, cancel; 
	
	public JTextField getLogin()
	{
		return ip;
	}
	public JTextField getPass()
	{
		return port;
	}
	public JButton getSubmit()
	{
		return submit;
	}
	public JButton getCancel()
	{
		return cancel;
	}
	public ConnectPanel(CardLayout cl, JPanel container)
	{
		JPanel grid = new JPanel(); 
		
		grid.setLayout(new GridLayout(3,1));
		
		JPanel user = new JPanel();
		ip = new JTextField("localhost"); 
		user.add(ip);
		
		JPanel pass = new JPanel();
		port = new JTextField("8300");
		port.setPreferredSize(ip.getPreferredSize());
		pass.add(port);
		
		JPanel buttons = new JPanel();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		buttons.add(submit);
		buttons.add(cancel);
		
		grid.add(user);
		grid.add(pass);
		grid.add(buttons);
		
		this.add(grid, BorderLayout.CENTER);
		this.setVisible(true);
	}
}


