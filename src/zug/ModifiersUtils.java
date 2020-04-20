package zug;

class ModifiersUtils {

    static String introStringPa(Player p, String name) {
        return "Player " + p.name() + " attempts ";
    }

    static String introStringPaAtt(Player p1, Player p2, Pawn pa1, Pawn pa2, String name) {
        return introStringPa(p1, name) + " with " + pa1.coor().toString() + " on player " + p2.name() + "'s " + pa2.coor().toString();
    }

    static boolean requiresPacman(Board b, Coor2d pos) {
        return !b.isIn(pos) || (b.isBorder(pos) && !b.isCorner(pos));
    }

    static ModifierConclusion binaryAttack(ModifierIntroduction mi, int dist, String name) {
        String cclStr = ModifiersUtils.introStringPaAtt(mi.p1, mi.p2, mi.fpp1, mi.fpp2, name);

        if (mi.fpp1.coor().distanceTo(mi.fpp2.coor()) > dist)
            mi.setEr(ActionEndReason.TOO_FAR);

        mi.setEr(mi.fpp2.kill(mi.b));
        return mi.finish(cclStr);
    }

    static ModifierConclusion movePawn(ModifierIntroduction mi, Cardinal orientation, Cardinal... pattern) {
        // INITIALISATION
        Cardinal[] truePattern = orientation.rotate(pattern);
        Coor2d pos = mi.fpp1.coor();
        String cclStr = introStringPa(mi.p1, "displacement") + " [ ";

        // CALCUL
        for (Cardinal c : truePattern) {
            cclStr += c.name() + " ";
            pos = pos.add(c.vector());
            if (requiresPacman(mi.b, pos) && !mi.p1.isPacman()) mi.setEr(ActionEndReason.NOT_PACMAN);
            if (!mi.b.isIn(pos)) pos = mi.b.pacmanCoor(pos);
        }
        if (mi.b.isRestricted(pos)) pos = mi.b.shift(pos);
        if (mi.b.isObstructed(pos)) mi.setEr(ActionEndReason.OBSTRUCTED);
        cclStr += "] from " + mi.fpp1.coor().toString() + " to " + pos.toString();

        // APPLICATION SUR AFTER
        mi.b.setObstructed(mi.fpp1.coor(), false);
        mi.b.setObstructed(pos, true);
        mi.fpp1.moveTo(pos);

        return mi.finish(cclStr);
    }
}
