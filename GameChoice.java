package main;

import java.io.Serializable;

public class GameChoice implements Serializable
{
	/*******************	
	 * Choices:
	 * 	0 = Stand
	 *  1 = Hit
	 *  2 = Double down
	 *******************/ 
	
	private int choice;
	
	//	ID of game that player is in
	private int gameNo;
	//	Which player in the game
	private int playerNo;
	
	public GameChoice() {
		choice = -1;
		gameNo = -1;
		playerNo = -1;
	}
	
	public GameChoice(int choice, int gameNo, int playerNo) {
		this.choice = choice;
		this.gameNo = gameNo;
		this.playerNo = playerNo;
	}
	
	public void setChoice(int choice) {
		this.choice = choice;
	}
	
	public int getChoice() {
		return choice;
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
		System.out.format("GameNo: %d, PlayerNo: %d, Choice: %d\n", gameNo, playerNo, choice);
	}
}
