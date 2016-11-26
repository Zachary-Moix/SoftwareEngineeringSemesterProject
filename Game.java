package main;

import java.io.IOException;
import main.PlayerCard;
import main.Player;

//**************************************************************
//Requirements of Game class
//--------------------------------------------------------------
//Can manage up to five players
//Can manage a deck
//Can deal cards to player hands
//Can ask for hit
//Can calculate win-loss conditions
//*****Win: blackjack (3:2), player value > dealer value (1:1)
//*****Lose: value > 21, player value < dealer value
//--------------------------------------------------------------
//**************************************************************

/***************************************************************
 * Keywords sent to server:
 * ACTION: Notifies player of its turn to make an action 
 * WAIT: Notifies player to wait for its turn
 * 
 */

public class Game extends Thread {
	
	private Player[] players; //array of players in the game
	private Deck deck; //deck to be used in the game
	private Dealer dealer; //dealer AI for game
	private int numPlayers; //keeps track of how many players are currently in the game
	private int playersInCurrentRound; //snapshot of numPlayers at the time the round began
	private boolean[] activity; //keeps track of which players entered bets for the current round
	private int receivedBets; //number of received bets to expedite the waiting time during bet phase
	private String currentAction; //current player's action
	
	public Game() {
		setPlayers(new Player[5]);
		deck = new Deck();
		activity = new boolean[5];
		dealer = new Dealer();
		numPlayers = 0;
	}
	public Game(Player[] p)
	{
		setPlayers(p);
		deck = new Deck();
		dealer = new Dealer();
		dealer.setName("Dealer");
		dealer.setNumber(-1);
		numPlayers = 0;
	}
	
	public void initialDeal() {
		
		clearAllHands();
		
		System.out.println("Initial Deal...");
		for(int i = 0; i < playersInCurrentRound * 2; i++)
		{
			hit(getPlayers()[i % playersInCurrentRound]);
			//getPlayers()[i % playersInCurrentRound].hit(deck.draw());
			if(i % playersInCurrentRound == playersInCurrentRound - 1) {
				hit(dealer);
			}
		}
		//System.out.println("Initial Deal");
		//checkAllPlayersForBlackjack();
		
		for(int i = 0; i < playersInCurrentRound; i++) {
			System.out.println("Player " + i + ": " + players[i].getValue());
			try
			{
				players[i].getConnectionToClient().sendToClient("RESULT: Your current hand value is " + players[i].getValue());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//can just do a blanket =21 check because it's only called right after the initial deal
	public void checkAllPlayersForBlackjack() {
		System.out.println("Checking for BlackJack...");
		for(int i = 0; i < playersInCurrentRound; i++) {
			if(getPlayers()[i].hasBlackJack()) {
				//TODO notify server of result
			}
		}
	}
	
	public void hit(Player p){
		Card c = deck.draw();
		p.hit(c);
		
		try
		{
			sendCardDataToAllClients(c, p.getNumber());
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Conditions:
	/*	1: Player win by value
	 *  2: Player win by dealer bust
	 *  3: Player win by Blackjack
	 *  0: Tie
	 * -1: Player lose by value
	 * -2: Player lose by bust
	 * -3: Player lose by dealer Blackjack
	 */
	public int calcResult(Player p){
		
		//if dealer busts, all players win
		if(p.didBust()) {
			return -2;
		}
		if(p.hasBlackJack()) {
			return 3;
		}
		//if dealer has blackjack and player doesn't also have blackjack, player loses
		if(dealer.hasBlackJack()) {
			if(p.hasBlackJack())
				return -3;
			else
				return 0;
		}
		//if the dealer busts, all player who didn't bust win... players who did bust will be filtered out by now
		if(dealer.didBust()) {
			return 2;
		}
		//if player value beats dealer value, player wins
		if(p.getValue() > dealer.getValue()) {
			return 1;
		}
		//if dealer value beats player value, player loses
		if(p.getValue() < dealer.getValue()) {
			return -1;
		}
		//if player value equals dealer value, tie
		else {
			return 0;
		}
	}
	
	public Player[] getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	public int getNumPlayers() {
		return numPlayers;
	}
	public void addPlayer(Player p) {
		getPlayers()[numPlayers] = p;
		numPlayers++;
	}
	//game capacity = 5
	public boolean isFull() {
		return getPlayers().length == 5;
	}
	
	public void setCurrentAction(int action) {
		switch(action) {
			case 0:
				currentAction = "Stand";
				break;
			case 1: 
				currentAction = "Hit";
				break;
			case 2: 
				currentAction = "DoubleDown";
				break;
			default:
				currentAction = "Waiting";
				break;
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Game start!");
		
		
		
		while(numPlayers > 0) {
			try {
				clearAllRoundSpecificData();
				requestBets();
				System.out.println("Requesting Bets...");
				Long start_time = System.currentTimeMillis();
				Long timer = System.currentTimeMillis();
				while(timer - start_time < 30000 && receivedBets < numPlayers) {
					timer = System.currentTimeMillis();

					receivedBets = 0;
					for(int i = 0; i < numPlayers; i++) {
						if(activity[i])
							receivedBets++;
					}
					//System.out.println((timer-start_time) + "..." + receivedBets + ":" + numPlayers);
				}
				
				notifyOfEndBetPhase();
				
				System.out.println(receivedBets + " players placed a bet for this round.");
				playersInCurrentRound = numPlayers;
				System.out.println(playersInCurrentRound + " players are in the game for this round... it is possible that not all were active.");
				this.initialDeal();
				this.checkAllPlayersForBlackjack();
				
				for(int i = 0; i < playersInCurrentRound; i++) {
					System.out.println("Looking at player " + i + ": " + activity[i]);
					if(activity[i] == true) {
						//Sets the current action to Stand, notifies the player that it's their turn, then sets a timer for 1 minute during which the player can respond 
						setCurrentAction(-1);
						while(/*!currentAction.equals("Stand") && !players[i].didBust()*/ true) {
							//notify player of their turn
							setCurrentAction(-1);
							players[i].requestPlayerAction();
							System.out.println("Waiting for response from client " + players[i].getConnectionToClient().getId() + "...");
							start_time = System.currentTimeMillis();
							timer = System.currentTimeMillis();
							//players have 60 seconds to make a selection
							while(currentAction.equals("Waiting") && timer - start_time < 60000) { 
								//Wait for a response from the player
								//System.out.print("waiting//");
								//System.out.println("1");
								timer = System.currentTimeMillis();
							}
							if(currentAction.equals("Waiting")) {
								setCurrentAction(0);
							}
							System.out.println("Game resumed: Current Action " + currentAction);
							if(currentAction.equals("Hit")) {
								hit(players[i]);
								players[i].getConnectionToClient().sendToClient("RESULT: Your current hand value is: " + players[i].getValue());
							}
							else if(currentAction.equals("Stand")) {
								players[i].getConnectionToClient().sendToClient("WAIT: Turn complete. Please wait.");
								break;
							}
							
							if(players[i].didBust()) {
								players[i].getConnectionToClient().sendToClient("RESULT: Sorry, you busted. Value: " + players[i].getValue() + ".");
								break;
							}	
							System.out.println("Hand value: " + players[i].getValue() + "... Bust: " + players[i].didBust());
						}
					}
				}
			} catch(IOException ie) {
					//ie.printStackTrace();
			}
			dealersTurn();
			printResultsToConsole();
		}
	}
	
	public void requestBets() throws IOException {
		for(int i = 0; i < numPlayers; i++) {
			players[i].getConnectionToClient().sendToClient("ACTION: Place your bet.");
		}
	}
	
	public void notifyOfEndBetPhase() throws IOException {
		for(int i = 0; i < numPlayers; i++) {
			players[i].getConnectionToClient().sendToClient("WAIT: Betting phase has ended.");
		}
	}
	
	public int[] createBalanceUpdateArray() {
		
		return null;
	}
	
	public void printResultsToConsole() {
		
		for(int i = 0; i < playersInCurrentRound; i++) {
			System.out.println("Player " + (i+1) + ": " + getPlayers()[i].getValue());
		}
		System.out.println("Dealer: " + dealer.getValue());
		
		for(int i = 0; i < playersInCurrentRound; i++) {
			String outcome;
			int result = calcResult(players[i]);
			
			switch(result) {
			
			case 1:
				outcome = " beat the dealer!";
				break;
			case 2:
				outcome = " won because the dealer busted!";
				break;
			case 3: 
				outcome = " got Blackjack!";
				break;
			case 0:
				outcome = " tied the dealer.";
				break;
			case -1:
				outcome = " lost to the dealer.";
				break;
			case -2:
				outcome = " busted.";
				break;
			case -3:
				outcome = " lost because the dealer got Blackjack.";
				break;
			default:
				outcome = " unknown outcome.";

			}
			System.out.println("Player " + (i+1) + outcome);
		}
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	public void dealersTurn() {
		while(dealer.shouldHit()) {
			dealer.hit(deck.draw());
		}
	}
	
	public void clearAllHands() {
		for(int i = 0; i < numPlayers; i++){
			players[i].clearHand();
			//activity[i] = false;
		}
		dealer.clearHand();
	}
	
	public void clearAllRoundSpecificData() {
		clearAllHands();
		for(int i = 0; i < numPlayers; i++) {
			activity[i] = false;
		}
		receivedBets = 0;
		setCurrentAction(-1);
	}
	
	public boolean isPlayerActive(int i) {
		return activity[i];
	}
	
	public void playerActed(int i) {
		activity[i] = true;
		receivedBets++;
		System.out.println(receivedBets);
	}
	
	public boolean[] getActivityArray() {
		return activity;
	}
	
	//notifies all clients of the dealt card and who it was dealt to
	public void sendCardDataToAllClients(Card c, int playerNo) throws IOException {
		
		PlayerCard pc = new PlayerCard(playerNo, c);
		for(int i = 0; i < numPlayers; i++) {
			System.out.println(i + " ~ " + numPlayers);
			players[i].getConnectionToClient().sendToClient(pc);
		}
	}
}
