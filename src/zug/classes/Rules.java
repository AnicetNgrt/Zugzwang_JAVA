package zug.classes;

import utils.JsonUtils;

import java.util.HashMap;

public class Rules {

	private String name;
	private int maxWeight;
	private int maxAp;

	public Rules(String name, int mw, int map) {
		this.name = name;
		maxAp = map;
		maxWeight = mw;
	}

	public static Rules fromJson(String jsonPath) {
		JsonUtils<Rules> jUtils = new JsonUtils<>(Rules.class);
		return jUtils.readJson(jsonPath);
	}

	String name() {
		return name;
	}

	int maxWeight() {
		return maxWeight;
	}

	int maxAp() {
		return maxAp;
	}

	public String toJson(HashMap<String, String> pathes) {
		String path = pathes.get(this.getClass().getName()) + name + ".json";
		JsonUtils<Rules> jUtils = new JsonUtils<>(Rules.class);
		jUtils.writeJson(path, this);
		return path;
	}
}
