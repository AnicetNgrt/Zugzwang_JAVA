package zug;

import java.util.ArrayList;

class ModifierIntroduction {
    Game before;
    Game after;
    ArrayList<Player> players;
    ArrayList<ArrayList<Pawn>> pawns;
    boolean coherent;
    //shortcuts
    Player p1;
    Player p2;
    Pawn fpp1;
    Pawn fpp2;
    Board b;
    private ActionEndReason er;

    ModifierIntroduction(Game g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI) {
        coherent = true;
        before = g;
        after = g.copy();
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

    void setEr(ActionEndReason re) {
        if (er == ActionEndReason.SUCCESS) er = re;
    }

    ModifierConclusion finish(String cclStr) {
        if (er != ActionEndReason.SUCCESS) {
            cclStr += " FAILS : " + er.name();
        } else {
            cclStr += " SUCCESS";
        }
        return new ModifierConclusion(er, cclStr, before, after);
    }
}
