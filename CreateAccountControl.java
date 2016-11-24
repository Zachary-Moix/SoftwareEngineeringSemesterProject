package finalProject;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CreateAccountControl implements ActionListener
{
  private CardLayout cl;
  private JPanel container;
  private Client client;
  
  public CreateAccountControl(CardLayout cl, JPanel container, Client client)
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
      int count = container.getComponentCount();                            //************AGAIN, THIS CREATE A PROBLEM WITH GETTING THE RIGHT COMPONENT, JUST BE AWARE
      CreateAccountPanel lp = (CreateAccountPanel)container.getComponent(3);
      
      CreateAccountData userData = new CreateAccountData(lp.getUserName(), lp.getPassword());
      
      try
      {
        client.sendToServer(userData);
       
      } catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    else if(a.getText().contentEquals("Cancel"))
    {
      cl.show(container, "2"); 
    }
  }
}