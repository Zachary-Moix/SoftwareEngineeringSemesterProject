package main;

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

public class Game {
	
	private Player[] players;
	private Deck deck;
	private Player dealer;
	
	public Game(Players[] p)
	{
		players = p;
		deck = new Deck();
		dealer = new Player();
	}
	
	public void initialDeal() {
		for(int i = 0; i < players.length * 2; i++)
		{
			if(i == 0) {
				dealer.hand.addCard(deck.deal());
			}
			players[i % players.length].hand.addCard(deck.deal());
		}
		
		checkAllPlayersForBlackjack();
	}
	
	//can just do a blanket =21 check because it's only called right after the initial deal
	public void checkAllPlayersForBlackjack() {
		for(int i = 0; i < players.length; i++) {
			if(players[i].hand.getValue() == 21) {
				//notify server of player blackjack
			}
		}
	}
	
	public void hit(Player p){
		p.hand.addCard(deck.deal());
	}
	
	//Conditions:
	//Win:	returns 1
	//Lose:	returns 0
	//Draw: returns -1
	public int didPlayerWin(Player p){
		if(p.hand.getValue() > dealer.hand.getValue()) {
			return 1;
		}
		else if(p.hand.getValue() < dealer.hand.getValue()) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	public boolean didPlayerBust(Player p) {
		return (p.hand.getValue > 21);
	}
}
