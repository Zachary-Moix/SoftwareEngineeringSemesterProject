package main;

public class Dealer extends Player{

	public Dealer() {
		super(-1, "Dealer");
	}
	
	//AI for dealer hits... dealer hits until 17
	public boolean shouldHit() {
		return this.hand.getSum() < 17;
	}
}
