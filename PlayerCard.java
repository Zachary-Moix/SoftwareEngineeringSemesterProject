package main;

import java.io.Serializable;

public class PlayerCard implements Serializable
{
	int playerNo;
	Card c;
	
	PlayerCard(int playerNo, Card c) {
		this.playerNo = playerNo;
		this.c = c;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}
	
	public Card getCard() {
		return c;
	}
}
