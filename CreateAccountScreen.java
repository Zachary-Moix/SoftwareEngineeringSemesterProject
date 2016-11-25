package GUI;

import java.awt.*;

import javax.swing.*; 

public class CreateAccountScreen extends JFrame 
{
	private JTextField login, password, confirm; 
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
	
	public CreateAccountScreen()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel grid = new JPanel(); 
		
		grid.setLayout(new GridLayout(4,1));
		
		JPanel passC = new JPanel();
		confirm = new JTextField("Confirm Password");
		passC.add(confirm);
		
		JPanel user = new JPanel();
		login = new JTextField("Username"); 
		login.setPreferredSize(confirm.getPreferredSize());
		user.add(login);
		
		JPanel pass = new JPanel();
		password = new JTextField("Password");
		password.setPreferredSize(confirm.getPreferredSize());
		pass.add(password);
		
		
		JPanel buttons = new JPanel();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		buttons.add(submit);
		buttons.add(cancel);
		
		grid.add(user);
		grid.add(pass);
		grid.add(passC);
		grid.add(buttons);
		
		this.add(grid, BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args)
	{
		new CreateAccountScreen(); 
	}
}