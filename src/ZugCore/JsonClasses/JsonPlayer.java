package ZugCore.JsonClasses;

public class JsonPlayer /*implements Jsonable*/ {
    public String name;
    public String[] pawnsPaths;
    public String[] handPaths;
    public boolean isPacman;
    public boolean isBowBandaged;
    public int ap;
    public String rulesPath;

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
        json.put("name", name);
        json.put("pawnPaths", pawnsPaths);
        json.put("handPaths", handPaths);
        json.put("isPacman", isPacman);
        json.put("isBowBandaged", isBowBandaged);
        json.put("ap", ap);
        json.put("rulesPath", rulesPath);
        json.toJson(writer);
    }*/
}
