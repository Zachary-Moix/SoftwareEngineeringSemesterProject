package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectPanel extends JFrame
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
	public ConnectPanel()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		this.pack();
	}
	
	public static void main(String[] args)
	{
		new ConnectPanel(); 
	}
}


