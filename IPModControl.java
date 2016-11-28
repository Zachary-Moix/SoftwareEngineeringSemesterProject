/*package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class IPModControl implements ActionListener
{
  private CardLayout cl;
  private JPanel container;
  private Client client;
  
  public IPModControl(CardLayout cl, JPanel container, Client client)
  {
    this.cl = cl;
    this.container = container;
    this.client = client;
  }
  
  public void actionPerformed(ActionEvent ae)
  {
    JButton a = (JButton)ae.getSource();
    if(a.getText().equals("Change IP"))
    { 
      //assuming there is a getter for the port thats input from the user
      //client.setIP(ip);
    }
    if(a.getText().equals("Change Port"))
    { 
      //assuming there is a getter for the port thats input from the user
      client.setPort(port);
    }
  }
}*/