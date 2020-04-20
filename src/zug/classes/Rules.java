package zug.classes;

import utils.JsonUtils;

public class Rules {

	private int maxWeight;
	private int maxAp;

	public static Rules fromJson(String jsonPath) {
		JsonUtils<Rules> jUtils = new JsonUtils<>(Rules.class);
		return jUtils.readJson(jsonPath);
	}

	int maxWeight() {
		return maxWeight;
	}

	int maxAp() {
		return maxAp;
	}
}
