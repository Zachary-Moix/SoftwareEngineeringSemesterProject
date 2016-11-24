package finalProject;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainScreenControl implements ActionListener
{ 
  private CardLayout cl;
  private JPanel container;
  
  public MainScreenControl(CardLayout cl, JPanel container)
  {
    this.cl = cl;
    this.container = container;
  }

  @Override
  public void actionPerformed(ActionEvent ae)
  {    
    String command = ae.getActionCommand();
    
    if (command.equals("Login"))
    {
      cl.show(container, "2");
    }
    
    if (command.equals("Create"))
    {
      cl.show(container, "3");
    }
    
    if (command.equals("Change IP/Port"))
    {
      cl.show(container, "6");
    }
  }

}
