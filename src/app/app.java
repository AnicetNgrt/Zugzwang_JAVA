package app;

import zug.classes.Coor2d;
import zug.classes.Game;
import zug.classes.GameState;
import zug.classes.Rules;
import zug.enums.CardTypes;

import java.util.ArrayList;

public class app {

	public static void main(String[] args) {
		ArrayList<String> pNames = new ArrayList<>();
		pNames.add("Anicet");
		pNames.add("Thibo");

		Rules rules = new Rules("basic");

		Game g = new Game(pNames, rules);

		GameState gs = g.getCurrent();

		gs.addCardForPlayer("Anicet", CardTypes.SMALL_RIVERS, 0);
		gs.addCardForPlayer("Anicet", CardTypes.CONTACT, 0);
		gs.addCardForPlayer("Anicet", CardTypes.CLOCK_MAKER, 0);
		gs.addCardForPlayer("Anicet", CardTypes.ARCHER, 0);
		gs.addPawnsForPlayer("Anicet", new Coor2d(0, 0), new Coor2d(2, 5), new Coor2d(6, 14));

		gs.addCardForPlayer("Thibo", CardTypes.SMALL_RIVERS, 0);
		gs.addCardForPlayer("Thibo", CardTypes.CONTACT, 0);
		gs.addCardForPlayer("Thibo", CardTypes.CLOCK_MAKER, 0);
		gs.addCardForPlayer("Thibo", CardTypes.ARCHER, 0);
		gs.addPawnsForPlayer("Thibo", new Coor2d(2, 2), new Coor2d(3, 7), new Coor2d(9, 14));

		g.toJson("gameTest", "C:\\IdeaProjects\\Zugzwang\\src\\assets");

		//----------------------

		Game gj = Game.fromJson("C:\\IdeaProjects\\Zugzwang\\src\\assets\\gameTest\\gameTest.json");
		gj.toJson("gameTestCopy", "C:\\IdeaProjects\\Zugzwang\\src\\assets");
	}
}
