package zug;

import java.util.HashSet;

import exceptions.HeavyHandException;

public class Player {
	private String name;
	private HashSet<Pawn> pawns;
	private HashSet<Card> hand;
	private Game game;
	private boolean isPacman;
	private boolean isBowBandaged;
	private int ap;
	
	void addPawn(Pawn p) {
		pawns.add(p);
	}
	
	int handWeight() {
		int w = 0;
		for(Card c:hand)
			w += c.type().weight();
		return w;
	}
	
	void addCard(Card c) throws HeavyHandException {
		if(handWeight() + c.type().weight() > game.rules().maxWeight()) throw new HeavyHandException();
		hand.add(c);
	}
	
	int ap() {
		return ap;
	}
	
	void pay(int ap) {
		this.ap -= ap;
	}

	public boolean isPacman() {
		return isPacman;
	}

	public void setPacman(boolean isPacman) {
		this.isPacman = isPacman;
	}
	
	public boolean isBowBandaged() {
		return isBowBandaged;
	}

	public void setBowBandaged(boolean isBowBandaged) {
		this.isBowBandaged = isBowBandaged;
	}
	
}