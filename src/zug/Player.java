package zug;

import exceptions.HeavyHandException;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Pawn> pawns;
	private ArrayList<Card> hand;
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

	boolean isPacman() {
		return isPacman;
	}

	void setPacman(boolean isPacman) {
		this.isPacman = isPacman;
	}

	boolean isBowBandaged() {
		return isBowBandaged;
	}

	void setBowBandaged(boolean isBowBandaged) {
		this.isBowBandaged = isBowBandaged;
	}

	String name() {
		return name;
	}

	public Pawn getPawn(Integer i) {
		if (i < 0 || i >= pawns.size()) return null;
		return pawns.get(i);
	}
}