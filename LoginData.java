package lab7out;


import java.io.*;

import javax.swing.*;

public class LoginData implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField username;
	private JPasswordField password;
	private boolean create = false; //states if the logindata is coming from create or login screen
	
	public LoginData(JTextField username, JPasswordField password )
	{
		this.username=username;
		this.password=password;
		
	}
	
	public void setuser (JTextField user)
	{
		username= user;
	}
	
	public void setcreate(boolean b)
	{
		create=b;
	}
	
	public boolean getcreate()
	{
		return create;
	}
	
	public void setpass (JPasswordField pass)
	{	
		password= pass;
		
	}

	public String getuser ()
	{
		return username.getText();
	}
	
	public String getpass ()
	{
		String t = "";
		char[] n= password.getPassword();
			
		for (int i=0; i< n.length; i++)
		{
			t += n[i];
		}
		
		return t;
	}
	
}
