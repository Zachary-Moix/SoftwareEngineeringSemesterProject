package main;

import java.awt.*;
import javax.swing.*;
import ocsf.client.*;

public class Client2 extends AbstractClient
{
	JTextArea log;
	JScrollPane scroll;
	int playerNo, gameNo;
	public Client2()
	{
		super("localhost", 8300);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 instanceof String) {
			String str = (String)arg0;
			if(str.startsWith("GAME")) {
				writeToLog("Game: " + str.substring(6,str.length()));
				gameNo = Integer.parseInt(str.substring(6));
			}
			else if(str.startsWith("PLAYER")) {
				writeToLog("Player: " + str.substring(8,str.length()));
				playerNo = Integer.parseInt(str.substring(8));
			}
			else if(str.startsWith("ACTION:")) {
				writeToLog(str.substring(8, str.length() - 1));
			}
			else if(str.startsWith("WAIT:")) {
				writeToLog(str.substring(6, str.length() -1 ));
			}
			else if(str.startsWith("RESULT:")){
				writeToLog(str.substring(8, str.length()));
			}
			else {
				writeToLog(str + "\n");
			}
		}
		else if(arg0 instanceof PlayerCard) {
			PlayerCard pc = (PlayerCard)arg0;
			Card card = pc.getCard();
			int number = pc.getPlayerNo();
			
			//TODO: The card should be displayed for the appropriate player
			String file = card.getValue() + card.getSuit();
			System.out.println(file);
			//GameScreen gs = (GameScreen)cont.getComponent(5);
			//gs.setPlayerCard(number, file);
			/*if(number == -1) {
				writeToLog("Dealer was dealt the " + card.getValue() + " of " + card.getSuit());
			}
			else {
				writeToLog("Player " + number + " was dealt the " + card.getValue() + " of " + card.getSuit());
			}	*/
		}
	}
	
	public void setScroll(JScrollPane jsp) {
		scroll = jsp;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}
	
	public int getGameNo() {
		return gameNo;
	}
	
	public void setLog(JTextArea jta) {
		this.log = jta;
	}
	
	public void writeToLog(String str) {
		log.append(str + "\n");
		JScrollBar bar = scroll.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
	}
}
