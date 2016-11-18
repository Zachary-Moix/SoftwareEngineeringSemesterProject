package main;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import ocsf.server.*;
import main.Player;
import main.GameData;
import main.LoginData;

public class Server extends AbstractServer{

	private JTextArea log;
	private JLabel status;
	private Database db;
	//a number of connections so that the server can easily identify which player sends messages
	private int[] clientIDs;
	private int numPlayers;
	//private Game[] games;
	Game g;
	
	public Server() {
		super(12345);
		
		db = new Database();
		
		clientIDs = new int[100];
		numPlayers = 0;
		g = new Game();
		//games = new Game[20];
	}
	
	public Server(int port) {
		super(port);

		db = new Database();

		clientIDs = new int[100];
		numPlayers = 0;
		g = new Game();
		//games = new Game[20];
	}
	
	public void setCurrentPlayer(Player p) {
		
	}
	
	public void setLog(JTextArea log) {
		this.log = log;
	}
	
	public JTextArea getLog() {
		return log;
	}
	
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	public JLabel getStatus() {
		return status;
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) 
	{
		// TODO Auto-generated method stub
		
		
		Object obj = arg0;
		
		if (obj instanceof LoginData) //if object sent to server is loginData
		{
		
		 
		LoginData ld= (LoginData)arg0;
		String user = ld.getuser();
		String pss = ld.getpass();
	    
	    //check if logindata came from create user or loginpanel
	    Boolean createflag = ld.getcreate();
	    
	    if(createflag) //logindata came from create panel
	    {
	    	//check if username password pair already exists
	    	 
	    	String test= "select * from users where username= '" + user + "' and password = aes_encrypt('"+ pss + "', 'key')";
	    	
	    	 ArrayList<String> result = db.query(test); //array list for result set
	    	 
	    	 if(result.get(0)==null) //no username password pair found, add username password pair to database
	    	 {
	    		 test = "insert into users values('" + user + "', aes_encrypt('" + pss +"', 'key'))";
	    		 
	    		 boolean result1 = db.executeDML(test); //execute
	 			
	 			if (result1)
	 			{
	 				try 
	 				{
						arg1.sendToClient("Success!!"); //user successfully created new username password pair
					} catch (IOException e) 
	 				{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //account created and login
	 			} 
	 			else
					try
	 			{
						arg1.sendToClient("Error!!"); 
					}
	 			catch (IOException e)
	 			{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		 
	    		 
	    	 }
	    	 else //there was already a username password pair in system
	    	 { 
	    		        try
	    		       {
	    		          arg1.sendToClient("Incorrect Username or Password");
	    		        }
	    		       catch (IOException e)
	    		       {
	    			  e.printStackTrace();
	    		        }
	    		  
	    	 }	
	    }
	    else //login data came from loginpanel
	    {	//see if there is a username password pair
	    
	    String test=  "select * from user where username= '" + user + "' and password = aes_encrypt('"+ pss + "', 'key')";
	    
	    ArrayList<String> result = db.query(test);

	    if (result.get(0)!=null) //not empty set returned, username password pair found, valid login
	    {
	       try
	      {
		  arg1.sendToClient("Valid Login"); //valid login
	       } 
	       catch (IOException e)
	       {
		  e.printStackTrace();
	        }
	       
	     }
	     else  //empty set returned invalid login, no username password pair found
	     {
	        try
	       {
	          arg1.sendToClient("Incorrect Username or Password");
	        }
	       catch (IOException e)
	       {
		  e.printStackTrace();
	        }
	  }
	    }
	} //end of instance of login data
	    
	//update balance
	/*if (obj instanceof Update) //update object that holds the username of client that sent message to server, and balance to update their balance in database, they won a hand
	{
		Update u = new Update();
		//get new value for balance
		double balance = 1000.00;
		
		//get username to set new balance
		String name = " ";
		
		String dml = "update users set balance =" +balance+" where username = '"+ name +"'";
		
		boolean b = db.executeDML(dml);
		
		 if (b) //dml successful, balance updated
		    {
		       try
		      {
			  arg1.sendToClient("Balance Updated"); //valid login
		       } 
		       catch (IOException e)
		       {
			  e.printStackTrace();
		        }
		       
		     }
		     else  //balance not updated
		     {
		        try
		       {
		          arg1.sendToClient("Balance NOT Updated");
		        }
		       catch (IOException e)
		       {
			  e.printStackTrace();
		        }
		  }
	}*/
	
		if(arg0 instanceof GameChoice) {
			GameChoice choice = (GameChoice)arg0;
			g.setCurrentAction(choice.getChoice());
			g.notify();
		}
	    
	}  //end of handlemessage
	
	protected void listeningException(Throwable exception) {
		System.out.println("Listening Exception: " + exception);
		exception.printStackTrace();
	}
	
	protected void serverStarted() { 
		log.append("Server started.\n");
	}
	
	protected void serverStopped() { 
		log.append("Server stopped.\n");
	}
	
	protected void serverClosed() { 
		log.append("Server closed.\n");
	}
	
	protected void clientDisconnected(ConnectionToClient client) {
		
		numPlayers--;
	}
	
	protected void clientConnected(ConnectionToClient client)
	{
		
		try {
			if(gameHasOpening()) {
				Player player = new Player(client, numPlayers);
				client.sendToClient("GAME: 0");
				client.sendToClient("PLAYER: 0");
				clientIDs[numPlayers] = (int) client.getId();
				numPlayers++;
				g.addPlayer(player);
			}
			else {
				client.sendToClient("ERROR: Game is full. Please wait...");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		
		/*int x = gameHasOpening();
		if(x == -1) {
			if(games.length < 20) {
				Game g = new Game();
				games[games.length] = g;
				Player player = new Player(client, numPlayers);
				connections[numPlayers] = client;
				numPlayers++;
				games[games.length].addPlayer(player);
				player.getConnectionToClient().sendToClient("GAME: " + 0 );
				player.getConnectionToClient().sendToClient("PLAYER: " 0);
				g.run();
			}
			else {
				client.sendToClient("ERROR: All games currently full, please wait");
			}
		}
		else {
			Player player = new Player(client, numPlayers);
			games[x].addPlayer(player);
			connections[numPlayers] = client;
			numPlayers++;
			player.getConnectionToClient().sendToClient("GAME: " + x);
			player.getConnectionToClient().sendToClient("Player: " + games[x].getNumPlayers());
		}*/
	}
	
	private boolean gameHasOpening() {
		
		return g.getNumPlayers() < 5;
		
		/*for(int i = 0; i < games.length; i++) {
			if(!(games[i].isFull())) {
				return i;
			}
		}
		return -1;*/
	}
	
}
