package GUI;

import java.awt.*;

import javax.swing.*; 

public class LoginScreen extends JFrame 
{
	private JTextField login, password; 
	private JButton submit, cancel; 
	
	public JTextField getLogin()
	{
		return login;
	}
	public JTextField getPass()
	{
		return password;
	}
	public JButton getSubmit()
	{
		return submit;
	}
	public JButton getCancel()
	{
		return cancel;
	}
	public LoginScreen()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel grid = new JPanel(); 
		
		grid.setLayout(new GridLayout(3,1));
		
		JPanel user = new JPanel();
		login = new JTextField("Username"); 
		user.add(login);
		
		JPanel pass = new JPanel();
		password = new JTextField("Password");
		pass.add(password);
		
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
		new LoginScreen(); 
	}
}
