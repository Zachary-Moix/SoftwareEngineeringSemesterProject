package main;

import java.awt.*;

import javax.swing.*; 

public class LoginScreen extends JPanel
{
	private JTextField login;
	private JPasswordField password; 
	private JButton submit, cancel; 
	private JLabel error;
	
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
	
	public void setError(String err) {
		error.setText(err);
		System.out.println("????");
	}
	
	public void clearTextFields() {
		login.setText("");
		password.setText("");
	}
	public LoginScreen(CardLayout cl, JPanel container, Client client, JFrame frame)
	{		
		this.setLayout(new BorderLayout());
		LoginControl lcs = new LoginControl(cl, container, client, frame);
		
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
		submit.addActionListener(lcs);
		cancel = new JButton("Cancel");
		cancel.addActionListener(lcs);
		buttons.add(submit);
		buttons.add(cancel);
		
		
		
		grid.add(u);
		grid.add(user);
		grid.add(p);
		grid.add(pass);
		
		JPanel jp = new JPanel();
		jp.add(grid);

		error = new JLabel();
		p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p.add(error);
		this.add(p, BorderLayout.NORTH);
		
		this.add(buttons, BorderLayout.SOUTH);
		this.add(jp, BorderLayout.CENTER);
	}
}
