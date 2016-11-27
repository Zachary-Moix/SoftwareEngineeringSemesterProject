package finalProject;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class GameScreenControl implements ActionListener
{
  private CardLayout cl;
  private JPanel container;
  private Client client;
  private JComboBox bets;
  
  public GameScreenControl(CardLayout cl, JPanel container, Client client, JComboBox bets)
  {
    this.cl = cl;
    this.container = container;
    this.client = client;
    this.bets = bets;
  }

  @Override
  public void actionPerformed(ActionEvent ae)
  {
    JButton a = (JButton)ae.getSource();
    if(a.getText().equals("Place Bet"))
    { 
      //get bet amount
      String betSelection = bets.getSelectedItem().toString();
      int bet = 0;
      if(betSelection.contentEquals("$5"))
      {
       bet = 5; 
      }
      else if(betSelection.contentEquals("$10"))
      {
        bet = 10;
      }
      else if(betSelection.contentEquals("$20"))
      {
        bet = 20;
      }
      else if(betSelection.contentEquals("$50"))
      {
        bet = 50;
      }
      else if(betSelection.contentEquals("$100"))
      {
        bet = 100;
      }
      
      BetData bd = new BetData(bet, client.getGameNo(), client.getPlayerNo(), "???");
      
      try
      {
        client.sendToServer(bd);
      } catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
    
    if(a.getText().equals("Hit"))
    {
      GameChoice gc = new GameChoice(1, client.getGameNo(), client.getPlayerNo());
      try
      {
        client.sendToServer(gc);
      } catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
    
    if(a.getText().equals("Stand"))
    {
      GameChoice gc = new GameChoice(0, client.getGameNo(), client.getPlayerNo());
      try
      
      {
        client.sendToServer(gc);

      } catch (IOException e1)
      {

        e1.printStackTrace();
      } 
    }
    
  }
}
