package ZwangApp;

import Utils.NetworkUtils;
import Utils.TUI;
import ZwangCore.Classes.Coor2d;
import ZwangCore.Classes.Game;
import ZwangCore.Classes.GameState;
import ZwangCore.Classes.Rules;
import ZwangCore.Enums.CardTypes;

import java.util.ArrayList;

public class App {

	public static void main(String[] args) {
		String out;
		String in;
		String host = "127.0.0.1";
		int port = 0;

		out = "-----ZWANG-----\n";
		out += "1 - Be a server\n";
		out += "2 - Be a client\n";
		out += "3 - Be both\n";
		in = TUI.choiceList(out, "1", "2", "3");
		switch (in) {
			case "1":
				NetworkUtils.startServer(host, port);
				break;
			case "2":
				NetworkUtils.startClient(host, port);
				break;
			case "3":
				port = NetworkUtils.startServer(host, port);
				NetworkUtils.startClient(host, port);
				break;
		}
	}

	public static void jsonTest() {
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
