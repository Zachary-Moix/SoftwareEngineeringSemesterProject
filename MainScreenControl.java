package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainScreenControl implements ActionListener
{ 
  private CardLayout cl;
  private JPanel container;
  private JFrame frame;
  
  public MainScreenControl(CardLayout cl, JPanel container, JFrame frame)
  {
    this.cl = cl;
    this.container = container;
    this.frame = frame;
  }

  @Override
  public void actionPerformed(ActionEvent ae)
  {    
    String command = ae.getActionCommand();
    
    if (command.equals("Login"))
    {
      cl.show(container, "2");
      frame.setSize(300,200);
    }
    
    if (command.equals("Create Account"))
    {
      cl.show(container, "3");
      frame.setSize(300,200);
    }
    
    if (command.equals("Change IP/Port"))
    {
      cl.show(container, "6");
    }
  }

}
