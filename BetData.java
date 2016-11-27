package main;

import java.io.Serializable;

public class BetData implements Serializable
{
	int amt;
	int gameNo;
	int playerNo;
	String username;
	
	public BetData() {
		amt = 0;
		gameNo = -1;
		playerNo = -1;
	}
	
	public BetData(int amt, int gameNo, int playerNo, String username) {
		this.amt = amt;
		this.gameNo = gameNo;
		this.playerNo = playerNo;
		this.username = username;
	}
	
	public void setAmt(int amt) {
		this.amt = amt;
	}
	
	public int getAmt() {
		return amt;
	}
	
	public void setGameNo(int gameNo) {
		this.gameNo = gameNo;
	}
	
	public int getGameNo() {
		return gameNo;
	}
	
	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}
	
	public void print() {
		System.out.format("Amoun:t %d, Game: %d, Player: %d\n", amt, gameNo, playerNo);
	}
}
