package main;

import java.io.IOException;

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

public class Game implements Runnable{
	
	private Player[] players;
	private Deck deck;
	private Dealer dealer;
	private boolean isRunning;
	private int numPlayers;
	private Player currentPlayer;
	private String currentAction;
	
	public Game() {
		setPlayers(new Player[5]);
		deck = new Deck();
		
		dealer = new Dealer();
		numPlayers = 0;
	}
	public Game(Player[] p)
	{
		setPlayers(p);
		deck = new Deck();
		dealer = new Dealer();
		numPlayers = 0;
	}
	
	public void initialDeal() {
		for(int i = 0; i < numPlayers * 2; i++)
		{
			getPlayers()[i % numPlayers].hit(deck.draw());
			if(i % numPlayers == numPlayers - 1) {
				dealer.hit(deck.draw());
			}
		}
		//System.out.println("Initial Deal");
		//checkAllPlayersForBlackjack();
	}
	
	//can just do a blanket =21 check because it's only called right after the initial deal
	public void checkAllPlayersForBlackjack() {
		for(int i = 0; i < numPlayers; i++) {
			if(getPlayers()[i].hasBlackJack()) {
				//TODO notify server of result
			}
		}
	}
	
	public void hit(Player p){
		p.hit(deck.draw());
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
	
	public boolean getIsRunning() {
		return isRunning;
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
				currentAction = "Error";
				break;
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.initialDeal();
		this.checkAllPlayersForBlackjack();
		//try {
			for(int i = 0; i < numPlayers; i++) {
				
				//notify player of their turn
				//players[i].getConnectionToClient().sendToClient("YOURTURN");
				while(players[i].getValue() < 17) {
					players[i].hit(deck.draw());
				}
				//Sets the current action to Stand, notifies the player that it's their turn, then sets a timer for 1 minute during which the player can respond 
				//setCurrentAction("Stand");
				//players[i].requestPlayerAction();
				//wait(60000);
			}
		//} catch(InterruptedException ie) {
				//ie.printStackTrace();
		//}
		
		while(dealer.shouldHit()) {
			dealer.hit(deck.draw());
		}
		for(int i = 0; i < numPlayers; i++) {
			System.out.println("Player " + (i+1) + ": " + getPlayers()[i].getValue());
		}
		System.out.println("Dealer: " + dealer.getValue());
		
		for(int i = 0; i < numPlayers; i++) {
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
				outcome = "ERROR";
			
			
			
			}
			System.out.println("Player " + (i+1) + outcome);
		}
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		for(int i = 0; i < 2; i++) {
			Player p = new Player();
			g.addPlayer(p);
		}
		g.run();
	}
}
