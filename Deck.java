package main;

import main.Card;
import java.util.Random;

public class Deck
{
	private Card[] cards = new Card[52];
	private String[] suits = {"Diamond", "Heart", "Club", "Spade"};
	private int index;
	
	public Deck()
	{
		for(int i = 0; i < 4; i++)
		{
			for(int j = 1; j <= 13; j++)
			{
				cards[(i*13)+j-1] = new Card(j,suits[i]);
			}
		}
		index = -1;
		
		shuffle();
	}
	
	public void shuffle()
	{
		Random rand = new Random(System.currentTimeMillis());
		int one, two;
		//lots of swaps results in a shuffled deck
		for(int i = 0; i < 52 * 5; i++)
		{
			one = Math.abs(rand.nextInt() % 52);
			two = Math.abs(rand.nextInt() % 52);
			swap(one, two);
		}
		index = -1;
	}
	
	private void swap(int a, int b)
	{
		Card temp = cards[a];
		cards[a] = cards[b];
		cards[b] = temp;
	}
	
	public Card draw()
	{
		index++;
		if(index >= 52)
			shuffle();
		return cards[index];
	}
	
	public void printDeck()
	{
		for(int i = 0; i < 52; i++)
		{
			System.out.println(i+1 + ": " + cards[i].getValue() + ":" + cards[i].getSuit());
		}
	}
	
	/*
	 counts distribution of cards to see how evenly they ended up after being shuffled
	 public void countSpread()
	{
		int club = 0, spade = 0, heart = 0, diamond = 0;
		for(int i = 0; i < 52; i++)
		{
			if(cards[i].getSuit().equals("Club"))
			{
				club += i;
			}
			else if (cards[i].getSuit().equals("Spade"))
			{
				spade += i;
			}
			else if(cards[i].getSuit().equals("Heart"))
			{
				heart += i;
			}
			else if(cards[i].getSuit().equals("Diamond"))
			{
				diamond += i;
			}
		}
		System.out.println("H: " + heart + ", D: " + diamond + ", C: " + club + ", S: " + spade);
	}	*/
	
	public static void main(String args[])
	{
		Deck d = new Deck();
		d.shuffle();
	}
}
