package main;

import main.Card;

public class Hand
{
	private Card[] cards;
	private int size;
	
	public Hand()
	{
		//maximum hand size in blackjack is 11: A,A,A,A,2,2,2,2,3,3,3 = 21
		cards = new Card[12];
		size = 0;
	}
	
	public void addCard(Card c)
	{
		cards[size] = c;
		size++;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getSum()
	{
		int sum = 0;
		int countAces = 0;
		for(int i = 0; i < size; i++)
		{
			if(cards[i].getValue() >= 2 && cards[i].getValue() <= 10)
			{
				sum += cards[i].getValue();
			}
			else if(cards[i].getValue() >= 11 && cards[i].getValue() <= 13)
			{
				sum += 10;
			}
			else if(cards[i].getValue() == 1)
			{
				countAces++;
			}
		}
		//modifies the used value of the ace depending on the current sum
		while(countAces > 0)
		{
			if(sum == 10)
				if(countAces == 1)
					sum = 21;
				else
					sum += 1;
			else if(sum < 10)
				sum += 11;
			else if(sum > 10 )
				sum += 1;
			
			countAces--;
		}
		return sum;
	}
	
	/*public static void main(String[] args) {
		Hand h = new Hand();
		h.addCard(new Card(1, "Diamond"));
		h.addCard(new Card(13, "Heart"));
		h.getSum();
	}*/
}
