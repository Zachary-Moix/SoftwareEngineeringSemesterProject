package main;

import java.awt.CardLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ocsf.client.AbstractClient;

public class Client extends AbstractClient
{
  private CardLayout c1;
  private JPanel cont;
  private int gameNo, playerNo;
  private boolean isCurrentPlayer;
  private JFrame frame;
  
  public Client()
  {
    super("localhost",8300);
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
		      frame.setSize(200,200);
		    }
		    
		    else if(str.equals("Incorrect Username or Password"))
		    {
		    	System.out.println("Received incorrect");
		        LoginScreen ls = (LoginScreen)cont.getComponent(1);
		        ls.setError("Invalid Login");
		        ls.clearTextFields();
		    }
		    
		    else if(str.equals("New Account Created"))
		    {
		      //System.out.println("Login with new credentials...");    //*********JAKE LET ME KNOW WHAT I NEED TO DO TO SET UP LOGIN AFTER CREATING A NEW ACCOUNT
		     
		      c1.show(cont, "2");
		    }
		    
		    if(((String)arg0).equals("RESULT: Error!!"))
		    {
			CreateAccountScreen createAccountPanel = (CreateAccountScreen)cont.getComponent(2);     //******AGAIN WITH THE COMPONENT ISSUE
			//SET UP CREATE ACCOUNT PANEL AFTER CREDENTIALS BEING IN USE
			createAccountPanel.setErrorMsg ("Credentials In Use");
			createAccountPanel.clearTextFields();

			//notify of error?

			c1.show(cont, "3");
		    }
		    
		    /****************************************
		     * New server communications start here!
		     ****************************************/
		    else if(str.equals("Joined game. Waiting for more players...")) {
		    	//TODO display game screen, but game hasn't started yet
		    	c1.show(cont,"5");
		    	frame.setSize(900,800);
		    	GameScreen gs = (GameScreen)cont.getComponent(4);
		    	gs.setMessage("Joined game. Waiting for more players.");
		    }
		    else if(str.equals("Received Bet Data")) {
		    	//TODO can do something notifying user that their bet has been received
		    	GameScreen gs = (GameScreen)cont.getComponent(4);
		    	gs.setMessage("Bet received.");
		    }
		    
		    //Server will respond to new connection notifying client of the game number
		    else if(str.startsWith("GAME:")) {
		    	this.gameNo = Integer.parseInt(str.substring(6, str.length()));
		    }
		    //Server will response to new connection notifying client of the player number
		    else if(str.startsWith("PLAYER: ")) {
		    	this.playerNo = Integer.parseInt(str.substring(8,str.length()));
		    	GameScreen gs = (GameScreen)cont.getComponent(4);
		    	gs.getPlayerLabel(playerNo).setFont(new Font("Serif", Font.ITALIC, 22));
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
		    	GameScreen gs = (GameScreen)cont.getComponent(4);
		    	gs.setMessage(message);
		    }
		    //After betting phase, server notifies all clients that the betting phase has ended
		    //Client will then wait for the "ACTION" message
		    else if(str.startsWith("WAIT: ")) {
		    	this.isCurrentPlayer = false;
		    	//This message reads "Betting phase has ended." or "Turn complete. Please wait."
		    	//It should be displayed somewhere on the clientGUI (eg. JLabel message)
		    	String message = str.substring(6, str.length());
		    	//TODO display this message somewhere
		    	GameScreen gs = (GameScreen)cont.getComponent(4);
		    	gs.setMessage(message);
		    }
		    //Will notify player of their result in the round
	    	//eg. "You busted" "You got Blackjack" etc.
		    else if(str.startsWith("RESULT: ")) {
		    	//Display somewhere on the GUI
		    	String message = str.substring(8, str.length());
		    	//TODO display this message somewhere
		    	GameScreen gs = (GameScreen)cont.getComponent(4);
		    	gs.setMessage(message);
		    }
		    else if(str.startsWith("BALANCE: ")) { 
		    	String balance = str.substring(9, str.length());
		    	//TODO display the balance somewhere game screen and check balance screen
		    	GameScreen gs = (GameScreen)cont.getComponent(4);
		    	gs.updateBalance(balance);
		    }

	  }
	  else if(arg0 instanceof PlayerCard) {
			PlayerCard pc = (PlayerCard)arg0;
			Card card = pc.getCard();
			int number = pc.getPlayerNo();
			
			//TODO: The card should be displayed for the appropriate player
			//NOTE: number = -1 means it belongs to the dealer, otherwise 0=0, 1=1, etc
			
			GameScreen gs = (GameScreen)cont.getComponent(4);
			String cardFile = (card.getValue() + card.getSuit() + ".png");
			//System.out.println(cardFile);
			gs.setPlayerCard(number, cardFile);
			//If this works I'm going to be so fucking happy you have no idea.
			
		}
		    /**********************************
		     * End of new server communications
		     **********************************/
  }
  
  public boolean getIsCurrentPlayer() {
	  return isCurrentPlayer;
  }
  
  public void setCardLayout(CardLayout cl)
  {
    this.c1 = cl;
  }
  
  public void setContainer(JPanel container)
  {
    this.cont = container;
  }
  
  public void setFrame(JFrame frame) {
	  this.frame = frame;
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