package Communication;

public enum CmdTypes {
    MYNAME("myname", 1, "name"),
    HOST("host", 1, "gameName", "playerCount"),
    JOIN("join", 1, "gameId"),
    ERROR("error", 1, "reason"),
    DISCONNECT("disconnect", 0),
    RETRY("retry", 1, "reason");

    private String name;
    private int strCount;
    private String[] params;

    CmdTypes(String n, int sc, String... p) {
        name = n;
        strCount = sc;
        params = p;
    }

    public String getName() {
        return name;
    }

    public int getStrCount() {
        return strCount;
    }

    public String[] getParams() {
        return params;
    }
}
