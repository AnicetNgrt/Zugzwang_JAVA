package ZugCore.JsonClasses;

public class JsonCard /*implements Jsonable*/ {
    public String typeName;
    public String[] actionsPaths;
    public String orientationName;
    public boolean shown;
    public int playedTurn;
    public int playedGame;

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
        json.put("typeName", typeName);
        json.put("actionsPaths", actionsPaths);
        json.put("orientationName", orientationName);
        json.put("shown", shown);
        json.put("playedTurn", playedTurn);
        json.put("playedGame", playedGame);
        json.toJson(writer);
    }*/
}