package zug;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Pawn> pawns;
	private ArrayList<Card> hand;
	private boolean isPacman;
	private boolean isBowBandaged;
	private int ap;
	private Rules rules;

	Player(String name, Rules rules) {
		this.name = name;
		pawns = new ArrayList<>();
		hand = new ArrayList<>();
		isPacman = true;
		isBowBandaged = false;
		this.rules = rules;
		ap = rules.maxAp();
	}

	Player(Player p) {
		this(p.name, p.rules);
		ap = p.ap;
		for (Pawn pa : p.pawns) {
			addPawn(new Pawn(pa, this));
		}
		for (Card ca : p.hand) {
			addCard(new Card(ca, this));
		}
		isPacman = p.isPacman;
		isBowBandaged = p.isBowBandaged;
	}

	void addPawn(Pawn p) {
		pawns.add(p);
	}

	int handWeight() {
		int w = 0;
		for (Card c : hand)
			w += c.type().weight();
		return w;
	}

	void addCard(Card c) {
		if (handWeight() + c.type().weight() > rules.maxWeight()) return;
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