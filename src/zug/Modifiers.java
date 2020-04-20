package zug;

import java.util.ArrayList;

enum Modifiers {
	//-----------------------------------------------------------------------------------------------------------
	M1((ModifierIntroduction mi, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(mi, orientation, orientation);
	}),
	//-----------------------------------------------------------------------------------------------------------
	MCavRight((ModifierIntroduction mi, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(mi, orientation, Cardinal.NORTH, Cardinal.NORTH, Cardinal.EAST);
	}),
	//-----------------------------------------------------------------------------------------------------------
	MCavLeft((ModifierIntroduction mi, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(mi, orientation, Cardinal.NORTH, Cardinal.NORTH, Cardinal.WEST);
	}),
	//-----------------------------------------------------------------------------------------------------------
	MClockLeft((ModifierIntroduction mi, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(mi, orientation, Cardinal.NORTH, Cardinal.NORTH, Cardinal.NORTH, Cardinal.WEST);
	}),
	//-----------------------------------------------------------------------------------------------------------
	MClockRight((ModifierIntroduction mi, Cardinal orientation) -> {
		return ModifiersUtils.movePawn(mi, orientation, Cardinal.NORTH, Cardinal.NORTH, Cardinal.NORTH, Cardinal.EAST);
	}),
	//-----------------------------------------------------------------------------------------------------------
	Contact((ModifierIntroduction mi, Cardinal orientation) -> {
		return ModifiersUtils.binaryAttack(mi, 1, "contact");
	}),
	//-----------------------------------------------------------------------------------------------------------
	Bandage((ModifierIntroduction mi, Cardinal orientation) -> {
		if (!mi.p1.isBowBandaged()) mi.setEr(ActionEndReason.CANT_BANDAGE_TWICE);
		mi.p1.setBowBandaged(true);
		return mi.finish(ModifiersUtils.introStringPa(mi.p1, "bandaging"));
	}),
	//-----------------------------------------------------------------------------------------------------------
	Unbandage((ModifierIntroduction mi, Cardinal orientation) -> {
		if (mi.p1.isBowBandaged()) mi.setEr(ActionEndReason.CANT_UNBANDAGE_TWICE);
		mi.p1.setBowBandaged(false);
		return mi.finish(ModifiersUtils.introStringPa(mi.p1, "unbandaging"));
	}),
	//-----------------------------------------------------------------------------------------------------------
	Bow((ModifierIntroduction mi, Cardinal orientation) -> {
		ModifierConclusion mc = ModifiersUtils.binaryAttack(mi, 2, "bow");
		ModifierToolKit unbandaging = new ModifierToolKit(Unbandage, mi.after, mi.p1, null, null, null, null);
		mi.after.tryRemovePlannedUntil(unbandaging, mi.after.clockNextTurn() + 4);
		mi.after.addPlanned(unbandaging, mi.after.clockNextTurn() + 4);
		return mc;
	});

	private ModifExpr expr;

	Modifiers(ModifExpr expr) {
		this.expr = expr;
	}

	ModifierConclusion execute(Game g, ArrayList<Integer> playersI, ArrayList<ArrayList<Integer>> pawnsI, Cardinal orientation) {
		ModifierIntroduction mi = new ModifierIntroduction(g, playersI, pawnsI);
		if (!mi.coherent) return new ModifierConclusion(ActionEndReason.INCOHERENT, "Incoherent data was given.", g, g);
		return expr.execute(mi, orientation);
	}
}
