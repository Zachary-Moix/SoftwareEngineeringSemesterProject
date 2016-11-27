package main;

import java.io.Serializable;

public class Card implements Serializable
{
	private int value;
	private String suit;
	
	public Card(int value, String suit)
	{
		this.value = value;
		this.suit = suit;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public String getSuit()
	{
		return suit;
	}
	
	/*public void print() {
		System.out.println(value + " of " + suit);
	}*/
}
