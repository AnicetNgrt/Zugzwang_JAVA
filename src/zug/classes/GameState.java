package zug.classes;

import utils.JsonUtils;
import zug.jsonClasses.JsonGameState;

import java.util.ArrayList;

public class GameState {

	private final int MAX_CLOCK;
	private Rules rules;
	private ArrayList<Player> players;
	private ArrayList<ModifierToolKit>[] planned;
	private Board board;
	private int clock;

	GameState(ArrayList<Player> players, Rules rules, Board board, int MAX_CLOCK) {
		this.MAX_CLOCK = MAX_CLOCK;
		clock = 0;
		this.players = players;
		planned = new ArrayList[MAX_CLOCK];
		for (int i = 0; i < MAX_CLOCK; i++) {
			planned[i] = new ArrayList<>();
		}
		this.board = board;
		this.rules = rules;
	}

	GameState(GameState g) {
		this(new ArrayList<>(), g.rules, new Board(g.board), g.MAX_CLOCK);
		for (Player p : g.players) {
			players.add(new Player(p));
		}
		int i = 0;
		for (ArrayList<ModifierToolKit> ml : g.planned) {
			for (ModifierToolKit mtk : ml) {
				planned[i].add(mtk);
			}
			i++;
		}
		clock = g.clock;
		board = new Board(g.board);
	}

	public static GameState fromJson(String jsonPath) {
		JsonUtils<JsonGameState> jUtils = new JsonUtils<>(JsonGameState.class);
		JsonGameState gsj = jUtils.readJson(jsonPath);

		ArrayList<Player> players = new ArrayList<>();
		for (String path : gsj.playersPaths)
			players.add(Player.fromJson(path));

		GameState gs = new GameState(players, Rules.fromJson(gsj.rulesPath), Board.fromTxt(gsj.boardPath), gsj.maxClock);

		int i = 0;
		for (ArrayList<String> ls : gsj.plannedPaths) {
			for (String path : ls) {
				gs.planned[i].add(ModifierToolKit.fromJson(path));
			}
			i++;
		}

		gs.clock = gsj.clock;
		return gs;
	}

	Rules rules() {
		return rules;
	}

	int clock() {
		return clock;
	}

	public int clockNextTurn() {
		return (clock + 8) - ((clock + 8) % 4);
	}

	int clockNextPlayer() {
		return (clock + 4) - ((clock + 8) % 4);
	}

	void addToClock(int time) {
		for (int c = clock + 1; c <= clock + time; c++) {
			for (ModifierToolKit mtk : planned[c]) {
				mtk.execute(this);
			}
		}
		clock += time;
	}

	Board board() {
		return board;
	}

	public void addPlanned(ModifierToolKit mtk, int clock) {
		planned[clock].add(mtk);
	}

	void tryRemovePlanned(ModifierToolKit mtk, int clock) {
		planned[clock].remove(mtk);
	}

	public void tryRemovePlannedUntil(ModifierToolKit mtk, int maxClock) {
		for (int c = clock; c <= maxClock; c++) {
			tryRemovePlanned(mtk, c);
		}
	}

	public Player getPlayer(int pI) {
		if (pI < 0 || pI >= players.size()) return null;
		return players.get(pI);
	}

	public int indexOfPlayer(Player p) {
		return players.indexOf(p);
	}
}
