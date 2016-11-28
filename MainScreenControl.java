package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class MainScreenControl implements ActionListener
{ 
  private CardLayout cl;
  private JPanel container;
  private JFrame frame;
  private Client client;
  
  public MainScreenControl(CardLayout cl, JPanel container, Client client, JFrame frame)
  {
    this.cl = cl;
    this.container = container;
    this.frame = frame;
    this.client = client;
  }

  @Override
  public void actionPerformed(ActionEvent ae)
  {    
	  if(ae.getSource() instanceof JButton) {
		  String command = ae.getActionCommand();
		    
		    if (command.equals("Login"))
		    {
		      cl.show(container, "2");
		      frame.setSize(300,200);
		    }
		    
		    else if (command.equals("Create Account"))
		    {
		      cl.show(container, "3");
		      frame.setSize(300,200);
		    }
		    
		    else if(command.equals("Connect")) {
		    	System.out.println("Attempting to connect to server on " + client.getHost());
		    	try
				{
					client.openConnection();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	  }
	  else if(ae.getSource() instanceof JMenuItem) {
		  String host = JOptionPane.showInputDialog("Enter IP: ", "localhost");
		  if(!(host == null)) {
			  client.setHost(host);
		  }
	  }
  }
}
