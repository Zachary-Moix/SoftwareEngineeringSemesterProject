package main;

import java.awt.*;

import javax.swing.*; 

public class LoginScreen extends JFrame 
{
	private JTextField login;
	private JPasswordField password; 
	private JButton submit, cancel; 
	
	public JTextField getLogin()
	{
		return login;
	}
	public JPasswordField getPass()
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
		
		grid.setLayout(new GridLayout(2,2));
		
		
		JPanel user = new JPanel();
		login = new JTextField(10); 
		//login.setPreferredSize(confirm.getPreferredSize());\
		user.setLayout(new FlowLayout(FlowLayout.LEADING));
		user.add(login);
		
		JPanel pass = new JPanel();
		password = new JPasswordField(10);
		//password.setPreferredSize(confirm.getPreferredSize());
		pass.setLayout(new FlowLayout(FlowLayout.LEADING));
		pass.add(password);
		
		JLabel ul = new JLabel("Username: ");
		JPanel u = new JPanel();
		u.add(ul);
		u.setLayout(new FlowLayout(FlowLayout.TRAILING));
		JLabel pl = new JLabel("Password: ");
		JPanel p = new JPanel();
		p.add(pl);
		p.setLayout(new FlowLayout(FlowLayout.TRAILING));
		JPanel buttons = new JPanel();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		buttons.add(submit);
		buttons.add(cancel);
		
		grid.add(u);
		grid.add(user);
		grid.add(p);
		grid.add(pass);

		this.add(buttons, BorderLayout.SOUTH);
		
		this.add(grid, BorderLayout.CENTER);
		this.setVisible(true);
		//this.setSize(400,300);
		this.pack();
	}
	
	public static void main(String[] args)
	{
		new LoginScreen(); 
	}
}
