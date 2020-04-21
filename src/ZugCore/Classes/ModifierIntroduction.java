package ZugCore.Classes;

import ZugCore.Enums.ActionEndReason;

import java.util.ArrayList;

public class ModifierIntroduction {
    public GameState before;
    public GameState after;
    public ArrayList<Player> players;
    public ArrayList<ArrayList<Pawn>> pawns;
    public ArrayList<Integer> playersI;
    public ArrayList<ArrayList<Integer>> pawnsI;
    public boolean coherent;

    //shortcuts
    public Player p1;
    public Player p2;
    public Pawn fpp1;
    public Pawn fpp2;
    public Board b;
    private ActionEndReason er;

    public ModifierIntroduction(GameState g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI) {
        this.playersI = playersI;
        this.pawnsI = pawnsI;
        coherent = true;
        before = g;
        after = new GameState(g);
        players = new ArrayList<>();
        for (int i : playersI) {
            players.add(after.getPlayer(i));
            if (after.getPlayer(i) == null) coherent = false;
        }
        pawns = new ArrayList<>();
        int pi = 0;
        for (ArrayList<Integer> lp : pawnsI) {
            pawns.add(new ArrayList<>());
            for (int i : lp) {
                if (after.getPlayer(pi) != null) {
                    pawns.get(i).add(after.getPlayer(pi).getPawn(i));
                    if (after.getPlayer(pi).getPawn(i) == null) coherent = false;
                } else {
                    coherent = false;
                }
            }
            pi++;
        }
        b = after.board();
        er = ActionEndReason.SUCCESS;

        //shortcuts
        p1 = players.get(0);
        p2 = players.get(1);
        fpp1 = pawns.get(0).get(0);
        fpp2 = pawns.get(1).get(0);
    }

    ActionEndReason getEr() {
        return er;
    }

    public void setEr(ActionEndReason re) {
        if (er == ActionEndReason.SUCCESS) er = re;
    }

    public ModifierConclusion finish(String cclStr) {
        if (er != ActionEndReason.SUCCESS) {
            cclStr += " FAILS : " + er.name();
        } else {
            cclStr += " SUCCESS";
        }
        return new ModifierConclusion(er, cclStr, before, after);
    }
}
