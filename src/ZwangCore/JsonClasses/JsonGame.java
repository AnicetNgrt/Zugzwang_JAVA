package ZwangCore.JsonClasses;

public class JsonGame /*implements Jsonable*/ {
    public String[] statesPaths;
    public String rulesPath;
    public String[] pNames;
    public int mainIndex;

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
        json.put("statesPaths", statesPaths);
        json.put("rulesPath", rulesPath);
        json.put("pNames", pNames);
        json.put("mainIndex", mainIndex);
        json.toJson(writer);
    }*/
}
