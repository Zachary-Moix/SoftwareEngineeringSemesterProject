package finalProject;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginControl implements ActionListener
{
  private CardLayout cl;
  private JPanel container;
  private Client client;
  
  public LoginControl(CardLayout cl, JPanel container, Client client)
  {
    this.cl = cl;
    this.container = container;
    this.client = client;
  }
  
  public void actionPerformed(ActionEvent ae)
  {
    JButton a = (JButton)ae.getSource();
    if(a.getText().equals("Submit"))
    { 
      int count = container.getComponentCount();                      //*****************POSSIBLE ISSUE HERE WITH SWITCHING TO THE RIGHT VIEW....MAYBE NOT THOUGH
      LoginScreen lp = (LoginScreen)container.getComponent(1);
      
      LoginData userData = new LoginData(lp.getLogin(), lp.getPass());
      
      try
      {

        //System.out.println(userData.getUsername()+" " + userData.getPassword());
        client.sendToServer(userData);
       
      } catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    else if(a.getText().contentEquals("Cancel"))
    {
      cl.show(container, "1"); 
    }
  }
}
