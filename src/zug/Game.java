package zug;

import java.util.ArrayList;

public class Game {

	private final int MAX_CLOCK = 99999;
	private Rules rules;
	private ArrayList<Player> players;
	private ArrayList<ModifierToolKit>[] planned;
	private ArrayList<TriggeredModifier> triggers;
	private Board board;
	private int clock;

	Rules rules() {
		return rules;
	}
	
	int clock() {
		return clock;
	}
	
	int clockNextTurn() {
		return (clock + 8) - ((clock + 8) % 4);
	}
	
	int clockNextPlayer() {
		return (clock + 4) - ((clock + 8) % 4);
	}
	
	void addToClock(int time) {
		for (int c = clock + 1; c <= clock + time; c++) {
			for (ModifierToolKit mtk : planned[c]) {
				mtk.execute();
			}
		}
		clock += time;
	}
	
	Board board() {
		return board;
	}
	
	void addPlanned(ModifierToolKit mtk, int clock) {
		planned[clock].add(mtk);
	}
	
	void addTrigger(TriggeredModifier tm) {
		triggers.add(tm);
	}
	
	void tryRemovePlanned(ModifierToolKit mtk, int clock) {
		planned[clock].remove(mtk);
	}

	void tryRemovePlannedUntil(ModifierToolKit mtk, int maxClock) {
		for (int c = clock; c <= maxClock; c++) {
			tryRemovePlanned(mtk, c);
		}
	}

	void tryRemoveTrigger(TriggeredModifier tm) {
		triggers.remove(tm);
	}

	public Player getPlayer(int pI) {
		if (pI < 0 || pI >= players.size()) return null;
		return players.get(pI);
	}

	public Game copy() {
		Game copy = new Game();
		return copy;
	}
}
