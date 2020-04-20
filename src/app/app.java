package app;

import zug.classes.Game;
import zug.classes.Rules;

import java.util.ArrayList;

public class app {

	public static void main(String[] args) {
		ArrayList<String> pNames = new ArrayList<>();
		pNames.add("Anicet");
		pNames.add("Thibo");

		Rules rules = new Rules("Test", 10, 4);

		Game g = new Game(pNames, rules);
	}
}
