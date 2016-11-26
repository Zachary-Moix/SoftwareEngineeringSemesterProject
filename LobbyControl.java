package finalProject;

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
    if(a.getText().equals("Join Game"))
    { 
      /*int count = container.getComponentCount();                            //************AGAIN, THIS CREATE A PROBLEM WITH GETTING THE RIGHT COMPONENT, JUST BE AWARE
      LobbyPanel lp = (LobbyPanel)container.getComponent(3);
      
      cl.show(container, "5");*/
      
      
    }
  }
}
