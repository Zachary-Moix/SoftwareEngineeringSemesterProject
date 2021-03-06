package main;

import java.io.Serializable;

public class GameData implements Serializable
{

	private int playerNo;
	private int gameNo;
	private String selection;

	public GameData() {
		
	}
	
	public GameData(int playerNo, int gameNo, String selection) {
		this.playerNo = playerNo;
		this.gameNo = gameNo;
		this.selection = selection;
	}
	
	public void setPlayerNo(int i) {
		playerNo = i;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}
	
	public void setGameNo(int i) {
		gameNo = i;
	}
	
	public int getGameNo() {
		return gameNo;
	}
	
	public void setSelection(String s) {
		selection = s;
	}
	
	public String getSelection() {
		return selection;
	}
	
	public void print() {
		System.out.format("Game: %d, Player: %d, Selection: %s\n", gameNo, playerNo, selection);
	}
}
