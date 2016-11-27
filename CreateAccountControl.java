package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CreateAccountControl implements ActionListener
{
  private CardLayout cl;
  private JPanel container;
  private Client client;
  private JFrame frame;
  
  public CreateAccountControl(CardLayout cl, JPanel container, Client client, JFrame frame)
  {
    this.cl = cl;
    this.container = container;
    this.client = client;
    this.frame = frame;
  }
  
  public void actionPerformed(ActionEvent ae)
  {
    JButton a = (JButton)ae.getSource();
    if(a.getText().equals("Submit"))
    { 
      //************AGAIN, THIS CREATE A PROBLEM WITH GETTING THE RIGHT COMPONENT, JUST BE AWARE
      CreateAccountScreen lp = (CreateAccountScreen)container.getComponent(2);
      
      LoginData ld = new LoginData(lp.getLogin(), lp.getPass());
      ld.setcreate(true);
      
      
      try
      {
        client.sendToServer(ld);
       
      } catch (IOException e)
      {
        e.printStackTrace();
      }
      lp.clearTextFields();
    }
    else if(a.getText().contentEquals("Cancel"))
    {
      cl.show(container, "1"); 
      frame.setSize(300,450);
    }
  }
}