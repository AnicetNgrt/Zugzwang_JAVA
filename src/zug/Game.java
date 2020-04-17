package zug;

import java.util.ArrayList;

public class Game {
	
	private final int MAX_CLOCK = 3996;
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
		this.clock += time;
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
		if(planned[clock].contains(mtk)) planned[clock].remove(mtk);
	}
	
	void tryRemovePlannedUntil(ModifierToolKit mtk, int maxClock) {
		for(int c = clock; c <= maxClock; c++) {
			tryRemovePlanned(mtk, c);
		}
	}
	
	void tryRemoveTrigger(TriggeredModifier tm) {
		if(triggers.contains(tm)) triggers.remove(tm);
	}
}
