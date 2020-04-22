package ZwangCore.JsonClasses;

public class JsonAction /*implements Jsonable*/ {
    public String modifierName;
    public int cost;

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
        json.put("modifierName", modifierName);
        json.put("cost", cost);
        json.toJson(writer);
    }*/
}