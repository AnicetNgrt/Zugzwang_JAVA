package ZwangCore.Classes;

import Utils.JsonUtils;
import ZwangCore.JsonClasses.JsonPlayer;

import java.util.ArrayList;
import java.util.HashMap;

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
		ap = rules.maxAp;
	}

	Player(Player p) {
        this(p.name, p.rules);
        ap = p.ap;
        for (Pawn pa : p.pawns) addPawn(new Pawn(pa, this));
        for (Card ca : p.hand) addCard(new Card(ca, this));
        isPacman = p.isPacman;
        isBowBandaged = p.isBowBandaged;
    }

	public static Player fromJson(String jsonPath) {
        JsonPlayer pj = (JsonPlayer) JsonUtils.readJson(jsonPath, JsonPlayer.class);

        assert pj != null;
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

	boolean addPawn(Pawn p) {
		if (getPawnCount() >= rules.maxPawn) return false;
		if (pawns.contains(p)) return false;
		pawns.add(p);
		return true;
	}

	int handWeight() {
		int w = 0;
		for (Card c : hand)
			w += c.type().weight();
		return w;
	}

	boolean addCard(Card c) {
		if (handWeight() + c.type().weight() > rules.maxWeight) return false;
		if (!rules.canDuplicateCard) {
			for (Card ca : hand) {
				if (ca.type().equals(c.type())) return false;
			}
		}
		giveCard(c);
		return true;
	}

	void giveCard(Card c) {
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

	public int getPawnCount() {
		return pawns.size();
	}

	public Card getCard(Integer i) {
		if (i < 0 || i >= hand.size()) return null;
		return hand.get(i);
	}

	public int getCardCount() {
		return hand.size();
	}

	public String toJson(HashMap<String, String> pathes) {
		int id = System.identityHashCode(this);
		String path = pathes.get("Player") + "player_" + name + "_" + id + ".json";
		JsonPlayer jp = new JsonPlayer();
		jp.ap = ap;
		jp.isBowBandaged = isBowBandaged;
		jp.isPacman = isPacman;
		jp.name = name;
		jp.rulesPath = pathes.get("Rules");
		jp.handPaths = new String[hand.size()];
		int i = 0;
		for (Card c : hand) {
			jp.handPaths[i++] = c.toJson(pathes);
		}
		jp.pawnsPaths = new String[pawns.size()];
		i = 0;
		for (Pawn pa : pawns) {
			jp.pawnsPaths[i] = pa.toJson(pathes, i++);
		}
		JsonUtils.writeJson(path, jp);
		return path;
	}

	public void turnReset() {
		ap = rules.maxAp;
		for (Card c : hand)
			c.turnReset();
	}
}