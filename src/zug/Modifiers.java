package zug;

import java.util.ArrayList;

interface ModifExpr {
	ActionEndReason execute(Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation);
}

class ModifiersUtils {
	static ActionEndReason movePawn(Game g, ArrayList<Pawn> receivers, Cardinal orientation, Cardinal... pattern) {
		Cardinal[] truePattern = orientation.rotate(pattern);
		Pawn receiver = (Pawn) receivers.toArray()[0];
		Coor2d pos = receiver.coor();
		Board b = g.board();
		Player owner = receiver.getOwner();
		for(Cardinal c:truePattern) {
			pos = pos.add(c.vector());
			if((!b.isIn(pos) || (b.isBorder(pos) && !b.isCorner(pos))) && !owner.isPacman()) return ActionEndReason.NOT_PACMAN;
			if(!b.isIn(pos)) pos = b.pacmanCoor(pos);
		}
		if(b.isRestricted(pos)) pos = b.shift(pos);
		if(b.isObstructed(pos)) return ActionEndReason.OBSTRUCTED;
		b.setObstructed(receiver.coor(), false);
		b.setObstructed(pos, true);
		receiver.moveTo(pos);
		return ActionEndReason.SUCCESS;
	}
}

public enum Modifiers {
	//-----------------------------------------------------------------------------------------------------------
	M1((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(g, receivers, orientation, orientation);
	}),
	//-----------------------------------------------------------------------------------------------------------
	MCavRight((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(g, receivers, orientation, Cardinal.NORTH, Cardinal.NORTH, Cardinal.EAST);
	}),
	//-----------------------------------------------------------------------------------------------------------
	MCavLeft((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(g, receivers, orientation, Cardinal.NORTH, Cardinal.NORTH, Cardinal.WEST);
	}),
	//-----------------------------------------------------------------------------------------------------------
	MClockLeft((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(g, receivers, orientation, Cardinal.NORTH, Cardinal.NORTH, Cardinal.NORTH, Cardinal.WEST);
	}),
	//-----------------------------------------------------------------------------------------------------------
	MClockRight((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(g, receivers, orientation, Cardinal.NORTH, Cardinal.NORTH, Cardinal.NORTH, Cardinal.EAST);
	}),
	//-----------------------------------------------------------------------------------------------------------
	Contact((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		Pawn sender = senders.get(0);
		Pawn receiver = receivers.get(0);
		if(sender.coor().distanceTo(receiver.coor()) > 1) return ActionEndReason.TOO_FAR;
		return receiver.kill(g.board());
	}),
	//-----------------------------------------------------------------------------------------------------------
	Bandage((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		if(!p1.isBowBandaged()) return ActionEndReason.CANT_BANDAGE_TWICE;
		p1.setBowBandaged(true);
		return ActionEndReason.SUCCESS;
	}),
	//-----------------------------------------------------------------------------------------------------------
	Unbandage((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		if(p1.isBowBandaged()) return ActionEndReason.CANT_UNBANDAGE_UNBANDAGED;
		p1.setBowBandaged(false);
		return ActionEndReason.SUCCESS;
	}),
	//-----------------------------------------------------------------------------------------------------------
	Bow((Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) -> {
		Pawn sender = senders.get(0);
		Pawn receiver = receivers.get(0);
		if(!sender.getOwner().isBowBandaged()) return ActionEndReason.BOW_UNBANDAGED;
		if(sender.coor().distanceTo(receiver.coor()) > 2) return ActionEndReason.TOO_FAR;
		ModifierToolKit unbandaging = new ModifierToolKit(Unbandage, g, p1, null, null, null, null);
		g.tryRemovePlannedUntil(unbandaging, g.clockNextTurn() + 4);
		g.addPlanned(unbandaging, g.clockNextTurn() + 4);
		return receiver.kill(g.board());
	});
	
	private ModifExpr expr;
	
	Modifiers(ModifExpr expr) {
		this.expr = expr;
	}

	ActionEndReason execute(Game g, Player p1, Player p2, ArrayList<Pawn> senders, ArrayList<Pawn> receivers, Cardinal orientation) {
		return expr.execute(g, p1, p2, senders, receivers, orientation);	
	}
}
