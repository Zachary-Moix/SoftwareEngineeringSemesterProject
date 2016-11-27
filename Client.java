package main;

import java.awt.CardLayout;

import javax.swing.JPanel;

import lab7out.CreateAccountPanel;
import lab7out.LoginPanel;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient
{
  private CardLayout c1;
  private JPanel cont;
  private int gameNo, playerNo;
  private boolean isCurrentPlayer;
  
  public Client(CardLayout cardLayout, JPanel container)
  {
    super("localhost",8300);
    
    c1 = cardLayout;
    cont = container;
  }

  @Override
  public void handleMessageFromServer(Object arg0)
  {
	  if(arg0 instanceof String) {
		  	String str = (String)arg0;
		    if(str.equals("Valid Login"))
		    {
		      //System.out.println("Login Complete...");
		      
		      //change view to lobby
		      c1.show(cont, "4");
		    }
		    
		    else if(str.equals("Incorrect Username or Password"))
		    {
		        //LoginPanel loginPanel = (LoginPanel)cont.getComponent(1);   //*****************JAKE LET ME KNOW WHAT I NEED TO DO TO SET UP THE PANEL CORRECTLY WITH INVALID CREDENTIALS
		        //loginPanel. setErrorMsg ("Incorrect Username or Password");
		        //loginPanel.clearTextFields();
		        
		        c1.show(cont, "2");
		    }
		    
		    else if(str.equals("New Account Created"))
		    {
		      //System.out.println("Login with new credentials...");    //*********JAKE LET ME KNOW WHAT I NEED TO DO TO SET UP LOGIN AFTER CREATING A NEW ACCOUNT
		     
		      c1.show(cont, "2");
		    }
		    
		    if(((String)arg0).equals("RESULT: Error!!"))
		    {
			CreateAccountPanel createAccountPanel = (CreateAccountPanel)cont.getComponent(3);     //******AGAIN WITH THE COMPONENT ISSUE
			//SET UP CREATE ACCOUNT PANEL AFTER CREDENTIALS BEING IN USE
			//createAccountPanel.setErrorMsg ("Credentials In Use");
			//createAccountPanel.clearTextFields();

			//notify of error?

			c1.show(cont, "3");
		    }
		    
		    if(((String)arg0).equals("Incorrect Username or Password"))
		    {
			CreateAccountPanel createAccountPanel = (CreateAccountPanel)cont.getComponent(3);     //******AGAIN WITH THE COMPONENT ISSUE
			//SET UP CREATE ACCOUNT PANEL AFTER CREDENTIALS BEING IN USE
			//createAccountPanel.setErrorMsg ("Incorrect Username or Password");
			//createAccountPanel.clearTextFields();

		      //notify of error?

			c1.show(cont, "3");
		    }
		    
		    /****************************************
		     * New server communications start here!
		     ****************************************/
		    else if(str.equals("Joined game. Waiting for more players...")) {
		    	//TODO display game screen, but game hasn't started yet
		    }
		    else if(str.equals("Received Bet Data")) {
		    	//TODO can do something notifying user that their bet has been received
		    }
		    
		    //Server will respond to new connection notifying client of the game number
		    else if(str.startsWith("GAME:")) {
		    	this.gameNo = Integer.parseInt(str.substring(6, str.length()));
		    }
		    //Server will response to new connection notifying client of the player number
		    else if(str.startsWith("PLAYER: ")) {
		    	this.playerNo = Integer.parseInt(str.substring(8,str.length()));
		    }
		    //Inside of game loop, server will request a single action from the client
		    //The client should only send actions to the server while this flag is true
		    //After sending a single action, the flag should be set to false.
		    else if(str.startsWith("ACTION: ")) {
		    	this.isCurrentPlayer = true;
		    	//This message reads "It is your turn to act" or "Place your bet"
		    	//It should be displayed somewhere on the clientGUI (eg. JLabel message)
		    	String message = str.substring(8, str.length());
		    	//TODO display this message somewhere
		    }
		    //After betting phase, server notifies all clients that the betting phase has ended
		    //Client will then wait for the "ACTION" message
		    else if(str.startsWith("WAIT: ")) {
		    	this.isCurrentPlayer = false;
		    	//This message reads "Betting phase has ended." or "Turn complete. Please wait."
		    	//It should be displayed somewhere on the clientGUI (eg. JLabel message)
		    	String message = str.substring(6, str.length());
		    	//TODO display this message somewhere
		    }
		    //Will notify player of their result in the round
	    	//eg. "You busted" "You got Blackjack" etc.
		    else if(str.startsWith("RESULT: ")) {
		    	//Display somewhere on the GUI
		    	String message = str.substring(8, str.length());
		    	//TODO display this message somewhere
		    }
		    else if(str.startsWith("BALANCE: ")) { 
		    	String balance = str.substring(9, str.length());
		    	//TODO display the balance somewhere game screen and check balance screen
		    }

	  }
	  else if(arg0 instanceof PlayerCard) {
			PlayerCard pc = (PlayerCard)arg0;
			Card card = pc.getCard();
			int number = pc.getPlayerNo();
			
			//TODO: The card should be displayed for the appropriate player
			//NOTE: number = -1 means it belongs to the dealer, otherwise 0=0, 1=1, etc
			/*if(number == -1) {
				log.append("Dealer was dealt the " + card.getValue() + " of " + card.getSuit() + ".\n");
			}
			else {
				log.append("Player " + number + " was dealt the " + card.getValue() + " of " + card.getSuit() + ".\n");
			}	*/
		}
		    /**********************************
		     * End of new server communications
		     **********************************/
  }
  
  public void setCardLayout(CardLayout cl)
  {
    this.c1 = cl;
  }
  
  public void setContainer(JPanel container)
  {
    this.cont = container;
  }

  //**************************************************************************
  //Functions used when needing to add playerNo/gameNo to data sent to server
  public int getPlayerNo() {
	  return playerNo;
  }
  
  public int getGameNo() {
	  return gameNo;
  }
  //**************************************************************************
  
  public void connectionException (Throwable exception) 
  {
    
  }
  public void connectionEstablished()
  {
    
  }

}



/*//*************************NOTES****************************
 * CARD VIEW NUMBER CORRESPONDENCE:
 * 1: MAIN SCREEN
 * 2: LOGIN SCREEN
 * 3: CREATE ACCOUNT SCREEN
 * 4: LOBBY
 * 5: GAME
 * 6. IP/PORT MOD
 * 
 * 
 */
