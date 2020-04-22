package ZwangCore.JsonClasses;

public class JsonGameState /*implements Jsonable*/ {
    public String rulesPath;
    public String[] playersPaths;
    public String[][] plannedPaths;
    public String boardPath;
    public int clock;

    /*@Override
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
        json.put("rulesPath", rulesPath);
        json.put("playersPaths", playersPaths);
        json.put("plannedPaths", plannedPaths);
        json.put("boardPath", boardPath);
        json.put("clock", clock);
        json.toJson(writer);
    }*/
}
