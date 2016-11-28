package main;

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
  private JFrame frame;
  private int bet;
  
  public GameScreenControl(CardLayout cl, JPanel container, Client client, JComboBox bets, JFrame frame)
  {
    this.cl = cl;
    this.container = container;
    this.client = client;
    this.bets = bets;
    this.frame = frame;
    bet = 5;
  }

  @Override
  public void actionPerformed(ActionEvent ae)
  {
	  System.out.println(ae.getSource());
	  if(ae.getSource() instanceof JButton) {
		  JButton a = (JButton)ae.getSource();
		  
		  if(client.getIsCurrentPlayer())
		  {
			  if(a.getText().equals("Place Bet"))
			  { 
			      BetData bd = new BetData(bet, client.getGameNo(), client.getPlayerNo());
			      
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
		  else {
			  GameScreen gs = (GameScreen)container.getComponent(4);
			  gs.setMessage("You must wait your turn!");
		  }
	  }
	  else if(ae.getSource() instanceof JComboBox) {
		  JComboBox jb = (JComboBox)ae.getSource();
		  String str = (String) jb.getSelectedItem();
		  str = str.substring(1,str.length());
		  this.bet = Integer.parseInt(str);
		  System.out.println("Bet set to $" + bet);
	  }
  }
}