package zug.classes;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import utils.JsonUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class Rules implements Jsonable {

	public String name;
	public int maxWeight;
	public int maxAp;
	public int maxClock;
	public int maxPawn;
	public int startPawn;
	public String boardSize;
	public boolean canDuplicateCard;
	public int playerCount;

	public Rules(String name) {
		this.name = name;
		playerCount = 2;
		maxWeight = 10;
		maxAp = 4;
		maxClock = 21;
		maxPawn = 4;
		startPawn = 3;
		boardSize = "STANDARD";
		canDuplicateCard = false;
	}

	public Rules() {
		this("noName");
	}

	public static Rules fromJson(String jsonPath) {
		return (Rules) JsonUtils.readJson(jsonPath, Rules.class);
	}

	public int startPawn() {
		return startPawn;
	}

	public String toJson(HashMap<String, String> pathes) {
		String path = pathes.get("Rules");

		JsonUtils.writeJson(path, this);
		return path;
	}

	@Override
	public String toJson() {
		final StringWriter writable = new StringWriter();
		try {
			this.toJson(writable);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return writable.toString();
	}

	@Override
	public void toJson(Writer writer) throws IOException {
		final JsonObject json = new JsonObject();
		json.put("name", name);
		json.put("playerCount", playerCount);
		json.put("maxWeight", maxWeight);
		json.put("maxAp", maxAp);
		json.put("maxClock", maxClock);
		json.put("startPawn", startPawn);
		json.put("maxPawn", maxPawn);
		json.put("boardSize", boardSize);
		json.put("canDuplicateCard", canDuplicateCard);
		json.toJson(writer);
	}

	public int maxClock() {
		return maxClock;
	}
}
