package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LobbyControl implements ActionListener
{
  private CardLayout cl;
  private JPanel container;
  private Client client;
  
  public LobbyControl(CardLayout cl, JPanel container, Client client)
  {
    this.cl = cl;
    this.container = container;
    this.client = client;
  }
  
  public void actionPerformed(ActionEvent ae)
  {
    JButton a = (JButton)ae.getSource();
    
    //either sends "REQUEST JOIN GAME" or "REQUEST CHECK BALANCE" to server
    String message = "REQUEST " + a.getText();
    message = message.toUpperCase();
    
    try
	{
		client.sendToServer(message);
	} catch (IOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
