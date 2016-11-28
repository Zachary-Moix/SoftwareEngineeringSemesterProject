package main;

import javax.swing.*;

import java.awt.Color;
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
	private JScrollPane pane;
	private Database db;
	//a number of connections so that the server can easily identify which player sends messages
	private int[] clientIDs;
	private int[] gameClientIDs;
	private int numConnectedClients;
	private String[] usernamesForClients; //needed to modify database balances
	private String[] usernamesForGameClients;
	private int[] betAmounts;
	private int numPlayers;
	private int numLoggedInUsers;
	private int playersPerGame;
	//private Game[] games;
	Game g;
	
	public Server() {
		super(12345);
		
		db = new Database();
		
		clientIDs = new int[5];
		gameClientIDs = new int[5];
		usernamesForClients = new String[5];
		usernamesForGameClients = new String[5];
		betAmounts = new int[5];
		numPlayers = 0;
		numConnectedClients = 0;
		numLoggedInUsers = 0;
		g = new Game(this);
		playersPerGame = 1;
		//games = new Game[20];
	}
	
	public void setPlayersPerGame(int i) {
		playersPerGame = i;
		writeToLog("Players per game set to " + playersPerGame);
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
	
	public void setPane(JScrollPane j) {
		this.pane = j;
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) 
	{
		// TODO Auto-generated method stub
		//writeToLog("Received message from client " + arg1.getId());
		
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
				writeToLog("Received LoginData (create): " + ld.getuser() + ":" + ld.getpass() + " from client " + arg1.getId() + ".");

				//check if username password pair already exists
	    	 
				String test= "select * from users where username= '" + user + "' and password = aes_encrypt('"+ pss + "', 'key')";
				System.out.println(test);
				ArrayList<String> result = db.query(test); //array list for result set
				System.out.println("After create query.");
				if(result.isEmpty()) //no username password pair found, add username password pair to database
				{
					test = "insert into users values('" + user + "', aes_encrypt('" + pss +"', 'key'), 1000)";
					System.out.println(test);
					boolean result1 = db.executeDML(test); //execute
					System.out.println("After update.");
					if (result1)
					{
						try 
						{
							arg1.sendToClient("New Account Created"); //user successfully created new username password pair						
							writeToLog("Client " + arg1.getId() + " create successful.");
							System.out.println("Create successful.");
						} catch (IOException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						} //account created and login
					} 
					else{
						System.out.println("Create unsuccessful... duplicate.");
						try
	 					{						
							writeToLog("Client " + arg1.getId() + " create unsuccessful.");
							arg1.sendToClient("RESULT: Error!!"); 
							System.out.println("Create unsuccessful.");
	 					}
	 					catch (IOException e)
	 					{
	 						// TODO Auto-generated catch block
	 						e.printStackTrace();
	 					}
					}
				}
				else //there was already a username password pair in system
				{ 
					System.out.println("Create unsuccessful... duplicate.");
					try
					{
						writeToLog("Client " + arg1.getId() + " create unsuccessful.");
						arg1.sendToClient("RESULT: Error!!");
						
					} catch (IOException e) {
						e.printStackTrace();
					}  
				}	
			}
			else //login data came from loginpanel
			{	//see if there is a username password pair
				writeToLog("Received LoginData: " + ld.getuser() + ":" + ld.getpass() + " from client " + arg1.getId() + ".");

				String test=  "select * from users where username= '" + user + "' and password = aes_encrypt('"+ pss + "', 'key')";
				System.out.println(test);
				ArrayList<String> result = db.query(test);
				System.out.println("After login query.");
				if (!result.isEmpty()) //not empty set returned, username password pair found, valid login
				{
					usernamesForClients[numLoggedInUsers] = user;
					clientIDs[numLoggedInUsers] = (int) arg1.getId();
					numLoggedInUsers++;
					System.out.println("Client IDs______");
					for(int i = 0; i < numLoggedInUsers; i++) {
						System.out.println(clientIDs[i]);
					}
					try
					{
						arg1.sendToClient("Valid Login"); //valid login
						writeToLog("Client " + arg1.getId() + " login success.");
						System.out.println("Login successful.");
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
						writeToLog("Client " + arg1.getId() + "Invalid Username or Password");
						System.out.println("Login unsuccessful.");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} //end of instance of login data
	    
	
		else if(arg0 instanceof BetData) {
			writeToLog("Bet received from client.");
			BetData bd = (BetData) arg0;
			try
			{
				arg1.sendToClient("Received Bet Data");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//remove amount from player's balance
			for(int i = 0; i < numLoggedInUsers; i++) {
				if(clientIDs[i] == arg1.getId()) {
					System.out.println("Bet removed $" + bd.getAmt() + " from " + usernamesForClients[i] + ".");
					String dml = "update users set balance = balance - " + bd.getAmt() + " where username = '" + usernamesForClients[i] + "';";
					Boolean b = db.executeDML(dml);
					if(b) {
						
						String query = "select balance from users where username = '" + usernamesForClients[i] + "';";
						System.out.println(query);
						ArrayList<String> result = db.query(query);
						if(!result.isEmpty()) {
							String balance = result.get(0);
							try
							{
								arg1.sendToClient("BALANCE: " + balance);
							} catch (IOException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			
			//assigns the bet amount to the correct player
			for(int i = 0; i < numPlayers; i++) {
				if(arg1.getId() == clientIDs[i]) {
					betAmounts[i] = bd.getAmt();
				}
			}
			notifyGameOfReceivedAction(arg1.getId());
		}
	
		else if(arg0 instanceof GameChoice) {
			writeToLog("Choice received from client.");
			try
			{
				arg1.sendToClient("Received Game Choice");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GameChoice choice = (GameChoice)arg0;
			System.out.println("Choice received from player: " + choice.getChoice());
			g.setCurrentAction(choice.getChoice());
			System.out.println("Attempting to resume game...");
		}
		
		else if(arg0 instanceof String) {
			
			String str = (String)arg0;
			
			if(str.equals("REQUEST JOIN GAME")) {
				writeToLog("Received request to join game from client " + arg1.getId());
				//When player attempts to join game, test if game has opening
				try {
					if(gameHasOpening()) {
						Player p = null;
						for(int i = 0; i < numLoggedInUsers; i++) {
							System.out.println("Search for: " + arg1.getId());
							System.out.println("IDs~~~~~");
							for(int j = 0; j < numLoggedInUsers; j++) {
								System.out.println(clientIDs[j]);
							}
							if(clientIDs[i] == arg1.getId()) {
								System.out.println("Search for: " + arg1.getId());
								p = new Player(arg1, usernamesForClients[i], numPlayers);
								numPlayers++;
								g.addPlayer(p);
								
								
								String query = "select balance from users where username = '" + usernamesForClients[i] + "';";
								System.out.println(query);
								ArrayList<String> result = db.query(query);
								if(!result.isEmpty()) {
									String balance = result.get(0);
									try
									{
										arg1.sendToClient("BALANCE: " + balance);
									} catch (IOException e)
									{
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
								break;
							}
						}
						if(p == null) {
							arg1.sendToClient("An unknown error has occurred. Please reconnect and try again.");
							//return;
						}
						arg1.sendToClient("Joined game. Waiting for more players...");
						arg1.sendToClient("GAME: 0");
						arg1.sendToClient("PLAYER: " + numPlayers);
						
						
						if(numPlayers >= playersPerGame) {
							g.start();
						}		
					}
					else {
						arg1.sendToClient("ERROR: Game is full. Please wait...");
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			//Activates when user pressed Check Balance button
			else if(str.equals("REQUEST CHECK BALANCE")) {
				writeToLog("Received balance check request from client " + arg1.getId());
				String username = "";
				//finds username in usernames array
				for(int i = 0; i < numConnectedClients; i++) {
					if(clientIDs[i] == (int)arg1.getId()) {
						username = usernamesForClients[i];
					}
				}
				String query = "select balance from users where username = '" + username + "';";
				ArrayList<String> result = db.query(query);
				
				if(!result.isEmpty()) {
					try
					{
						arg1.sendToClient("BALANCERESPONSE: " + result.get(0));
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	    
	}  //end of handlemessage
	
	protected void listeningException(Throwable exception) {
		System.out.println("Listening Exception: " + exception);
		exception.printStackTrace();
	}
	
	protected void serverStarted() { 
		writeToLog("Server started.");
		status.setText("Listening");
		status.setForeground(Color.green);
	}
	
	protected void serverStopped() { 
		writeToLog("Server stopped.");
	}
	
	protected void serverClosed() { 
		writeToLog("Server closed.");
	}
	
	protected void clientDisconnected(ConnectionToClient client) {
		
		writeToLog("Client disconnected");
		numConnectedClients--;
	}
	
	protected void clientConnected(ConnectionToClient client)
	{
		writeToLog("Client connected.");
		numConnectedClients++;
		/*try {
			if(gameHasOpening()) {
				Player player = new Player(client, numPlayers);
				//client.sendToClient("GAME: 0");
				//client.sendToClient("PLAYER: " + numPlayers);
				clientIDs[numPlayers] = (int) client.getId();
				numPlayers++;
				System.out.println("IDs~~~~~");
				for(int i = 0; i < numPlayers; i++) {
					System.out.println(clientIDs[i]);
				}
				g.addPlayer(player);
				if(numPlayers >= 2) {
					g.start();
				}
			}
			else {
				client.sendToClient("ERROR: Game is full. Please wait...");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		*/
		
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
	
	private void updatePlayerBalance(int amount, Player player) {
		
		String dml = "update users set balance =" + amount + " where username = '" + player.getName() + "'";
		
		boolean b = db.executeDML(dml);
		
		if (b) //dml successful, balance updated
		{
			try
		    {
				player.getConnectionToClient().sendToClient("Balance Updated"); //valid login
				writeToLog("Balance Update: " + player.getName() + "..." + amount);
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		       
		}
		else  //balance not updated
		{
			try
		    {
				player.getConnectionToClient().sendToClient("Balance NOT Updated");
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
	}
	
	public void writeToLog(String entry) {
		log.append(entry + "\n");
		JScrollBar v = pane.getVerticalScrollBar();
		v.setValue(v.getMaximum());
	}
	
	private void notifyGameOfReceivedAction(long clientID) {
		writeToLog("Notify game of bet from " + clientID);
		for(int i = 0; i < numPlayers; i++) {
			if(g.getPlayers()[i].getConnectionToClient().getId() == clientID) {
				g.getActivityArray()[i] = true;
			}
		}
	}
	
	public int getPlayerBetAmount(int i) {
		return betAmounts[i];
	}
	
	public void notifyAllClientsOfUpdatedBalance() throws IOException {
		for(int i = 0; i < numPlayers; i++) {
			String query = "select balance from users where username = '" + usernamesForClients[i] + "';";
			System.out.println(query);
			ArrayList<String> result = db.query(query);
			if(!result.isEmpty()) {
				String balance = result.get(0);
				g.getPlayers()[i].getConnectionToClient().sendToClient("BALANCE: " + balance);
			}
		}
	}
	
	public Database getDB() {
		return db;
	}
	
	public void playerDoubledDown(Player p) {
		
		for(int i = 0; i < numPlayers; i++) {
			if(p.getConnectionToClient().getId() == clientIDs[i]) {
				String dml = "update users set balance = balance - " + betAmounts[i] + " where username = '" + usernamesForClients[i] + "';";
				Boolean b = db.executeDML(dml);
				if(b) {
					String query = "select balance from users where username = '" + usernamesForClients[i] + "';";
					System.out.println(query);
					ArrayList<String> result = db.query(query);
					if(!result.isEmpty()) {
						String balance = result.get(0);
						try
						{
							p.getConnectionToClient().sendToClient("BALANCE: " + balance);
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				betAmounts[i] *= 2;
			}
			
		}
		
	}
}

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
