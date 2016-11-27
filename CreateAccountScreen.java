package main;

import java.awt.*;

import javax.swing.*;

//import lab7out.AccountCreationControl; 

public class CreateAccountScreen extends JPanel 
{
	private JTextField login;
	private JPasswordField password, confirm; 
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
	
	public CreateAccountScreen(CardLayout cl, JPanel container, Client client, JFrame frame)
	{
		CreateAccountControl cac = new CreateAccountControl(cl, container, client, frame);
		this.setLayout(new BorderLayout());
		JPanel grid = new JPanel(); 
		
		grid.setLayout(new GridLayout(3,2));
		
		JPanel passC = new JPanel();
		confirm = new JPasswordField(10);
		passC.setLayout(new FlowLayout(FlowLayout.LEADING));
		passC.add(confirm);
		
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
		JLabel clab = new JLabel("Confirm Password: ");
		JPanel c = new JPanel();
		c.setLayout(new FlowLayout(FlowLayout.TRAILING));
		c.add(clab);
		JPanel buttons = new JPanel();
		submit = new JButton("Submit");
		submit.addActionListener(cac);
		cancel = new JButton("Cancel");
		cancel.addActionListener(cac);
		buttons.add(submit);
		buttons.add(cancel);
		
		error = new JLabel("");
		JPanel pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pan.add(error);
		this.add(pan,BorderLayout.NORTH);
		
		grid.add(u);
		grid.add(user);
		grid.add(p);
		grid.add(pass);
		grid.add(c);
		grid.add(passC);
		
		JPanel jp = new JPanel();
		jp.add(grid);
		
		this.add(buttons, BorderLayout.SOUTH);
		
		this.add(jp, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void setErrorMsg(String err) {
		error.setText(err);
	}
	
	public void clearTextFields() { 
		login.setText("");
		password.setText("");
		confirm.setText("");
	}
}