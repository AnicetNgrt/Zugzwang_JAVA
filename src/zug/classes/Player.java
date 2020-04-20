package zug.classes;

import utils.JsonUtils;
import zug.jsonClasses.JsonPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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

	public static Player fromJson(String jsonPath) {
		JsonUtils<JsonPlayer> jUtils = new JsonUtils<>(JsonPlayer.class);
		JsonPlayer pj = jUtils.readJson(jsonPath);

		Player p = new Player(pj.name, Rules.fromJson(pj.rulesPath));
		for (String path : pj.pawnsPaths)
			p.pawns.add(Pawn.fromJson(path, p));

		for (String path : pj.handPaths)
			p.hand.add(Card.fromJson(path, p));

		p.isPacman = pj.isPacman;
		p.isBowBandaged = pj.isBowBandaged;
		p.ap = pj.ap;

		return p;
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

	public boolean isBowBandaged() {
		return isBowBandaged;
	}

	public void setBowBandaged(boolean isBowBandaged) {
		this.isBowBandaged = isBowBandaged;
	}

	String name() {
		return name;
	}

	public Pawn getPawn(Integer i) {
		if (i < 0 || i >= pawns.size()) return null;
		return pawns.get(i);
	}

	public String toJson(HashMap<String, String> pathes) {
		int id = new Random().nextInt(99);
		String path = pathes.get(this.getClass().getName()) + "player|" + name + "|" + id + ".json";
		JsonUtils<JsonPlayer> jUtils = new JsonUtils<>(JsonPlayer.class);
		JsonPlayer jp = new JsonPlayer();
		jp.ap = ap;
		jp.isBowBandaged = isBowBandaged;
		jp.isPacman = isPacman;
		jp.name = name;
		jp.rulesPath = pathes.get("Rules");
		jp.handPaths = new ArrayList<>();
		for (Card c : hand) {
			jp.handPaths.add(c.toJson(pathes));
		}
		jp.pawnsPaths = new ArrayList<>();
		int i = 0;
		for (Pawn pa : pawns) {
			jp.pawnsPaths.add(pa.toJson(pathes, i++));
		}
		jUtils.writeJson(path, jp);
		return path;
	}
}