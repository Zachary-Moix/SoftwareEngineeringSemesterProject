package main;

import java.io.IOException;

import ocsf.server.*;

public class Player {

	private String name;
	protected Hand hand;
	private ConnectionToClient ctc;
	private int number;
	
	public Player() {
		hand = new Hand();
	}

	public Player(int playerNumber, String username) {
		this.number = playerNumber;
		this.name = username;
		hand = new Hand();
		ctc = null;
	}
	
	public Player(ConnectionToClient ctc, String username, int playerNumber) {
		this.ctc = ctc;
		hand = new Hand();
		name = username;
		number = playerNumber;
	}
	
	public Player(String username) {
		setName(username);
		hand = new Hand();
	}
	
	public void hit(Card c) {
		hand.addCard(c);
	}
	
	public int getValue() {
		return hand.getSum();
	}
	
	public boolean hasBlackJack() { 
		return (hand.getSum() == 21 && hand.getSize() == 2);
	}
	
	public boolean didBust() {
		return (hand.getSum() > 21);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void clearHand() {
		this.hand.clear();
	}
	
	public ConnectionToClient getConnectionToClient() {
		return ctc;
	}
	
	public void requestPlayerAction() throws IOException {
		this.ctc.sendToClient("ACTION: It is your turn to play.");
	}
	
	public void setNumber(int n) {
		this.number = n;
	}
	
	public int getNumber() {
		return number;
	}
}
