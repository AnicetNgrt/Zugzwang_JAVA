package zug.classes;

import utils.JsonUtils;
import zug.enums.CardTypes;
import zug.enums.Cardinal;
import zug.jsonClasses.JsonGameState;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

    private Rules rules;
    private ArrayList<Player> players;
    private ArrayList<ModifierEmbed>[] planned;
    private Board board;
    private int clock;

    GameState(ArrayList<Player> players, Rules rules, Board board) {
        clock = 0;
        this.players = players;
        planned = new ArrayList[rules.maxClock()];
        for (int i = 0; i < rules.maxClock(); i++) {
            planned[i] = new ArrayList<>();
        }
        this.board = board;
        this.rules = rules;
    }

	GameState(GameState g) {
        this(new ArrayList<>(), g.rules, new Board(g.board));
        for (Player p : g.players) {
            players.add(new Player(p));
        }
        int i = 0;
        for (ArrayList<ModifierEmbed> ml : g.planned) {
            for (ModifierEmbed mtk : ml) {
                planned[i].add(mtk);
            }
            i++;
        }
		clock = g.clock;
		board = new Board(g.board);
	}

	public static GameState fromJson(String jsonPath) {
        JsonGameState gsj = (JsonGameState) JsonUtils.readJson(jsonPath, JsonGameState.class);

        ArrayList<Player> players = new ArrayList<>();
        for (String path : gsj.playersPaths)
            players.add(Player.fromJson(path));

        GameState gs = new GameState(players, Rules.fromJson(gsj.rulesPath), Board.fromJson(gsj.boardPath));

        int i = 0;
        for (String[] ls : gsj.plannedPaths) {
            for (String path : ls) {
                gs.planned[i].add(ModifierEmbed.fromJson(path));
            }
            i++;
        }

        gs.clock = gsj.clock;
        return gs;
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
        int prevTurn = clock % 4;
        for (int c = clock + 1; c <= clock + time; c++) {
            for (ModifierEmbed mtk : planned[c]) {
                mtk.execute(this);
            }
        }
        clock += time;
        int newTurn = clock % 4;
        if (prevTurn > newTurn) {
            for (Player p : players)
                p.turnReset();
        }
    }

    int playerIndex() {
        return (clock % 4) % players.size();
    }

    Board board() {
        return board;
    }

    public void addPlanned(ModifierEmbed mtk, int clock) {
        planned[clock].add(mtk);
    }

	void tryRemovePlanned(ModifierEmbed mtk, int clock) {
		planned[clock].remove(mtk);
	}

	public void tryRemovePlannedUntil(ModifierEmbed mtk, int maxClock) {
        for (int c = clock; c <= maxClock; c++) {
            tryRemovePlanned(mtk, c);
        }
    }

    public Player getPlayer(int pI) {
        if (pI < 0 || pI >= players.size()) return null;
        return players.get(pI);
    }

    public Player getPlayer(String name) {
        for (Player p : players)
            if (p.name().equals(name)) return p;
        return null;
    }

    public int indexOfPlayer(Player p) {
        return players.indexOf(p);
    }

    public boolean addCardForPlayer(String pName, CardTypes type, Cardinal orientation) {
        Player p = this.getPlayer(pName);
        if (p == null) return false;
        return p.addCard(type.getOne(orientation, p));
    }

    public boolean addCardForPlayer(String pName, CardTypes type, int i) {
        return addCardForPlayer(pName, type, Cardinal.values()[i]);
    }

    public boolean addPawnsForPlayer(String pName, Coor2d... crd) {
        Player p = this.getPlayer(pName);
        if (p == null) return false;
        boolean success = true;
        for (Coor2d coor : crd) {
            Pawn pa = new Pawn(coor, p);
            success = p.addPawn(pa);
            success = pa.correctPos(board) && success;
        }
        return success;
    }

    public String toJson(HashMap<String, String> pathes) {
        int id = System.identityHashCode(this);
        String path = pathes.get("GameState") + "gameState_" + id + ".json";
        JsonGameState jgs = new JsonGameState();
        jgs.boardPath = board.toJson(pathes);
        jgs.clock = clock;
        jgs.rulesPath = pathes.get("Rules");
        jgs.plannedPaths = new String[rules.maxClock()][];
        for (int i = 0; i < rules.maxClock(); i++) {
            jgs.plannedPaths[i] = new String[planned[i].size()];
            int j = 0;
            for (ModifierEmbed mtk : planned[i]) {
                jgs.plannedPaths[i][j++] = mtk.toJson(pathes);
            }
        }
        jgs.playersPaths = new String[players.size()];
        int i = 0;
        for (Player p : players) {
            jgs.playersPaths[i++] = p.toJson(pathes);
        }
        JsonUtils.writeJson(path, jgs);
        return path;
    }
}
