package Communication;

public enum CmdTypes {
    // NON-GAME COMMANDS
    CONNECTIONCONFIRM("connectconfirm", 0),
    MYNAMEIS("myname", 1, "name"),
    GIVEID("giveid", 1, "id"),
    HOST("host", 2, "gameName", "password", "maxPlayerCount", "spectatorsAllowed"),
    JOIN("join", 1, "gameId"),
    REQUESTLOBBIES("requestlobbies", 0, "count", "offset"),
    GIVEMYLOBBYDATA("givemylobbydata", 0),
    RECEIVELOBBYDATA("receivelobbydata", 1, "gameName", "partOfList", "maxPlayerCount", "spectatorsAllowed", "playerCount", "spectatorCount"),
    ERROR("error", 1, "reason"),
    DISCONNECT("disconnect", 0),
    RETRY("retry", 1, "reason"),
    PING("ping", 1, "message", "integer"),

    // GAME COMMANDS
    STARTGAME("startgame", 0),
    KICKFROMGAME("kickfromgame", 1, "nameInGame"),
    ADDPLAYER("addplayer", 1, "nameInGame", "isSpectator"),
    REMOVEPLAYER("removeplayer", 1, "nameInGame", "isSpectator");

    private final String name;
    private final int strCount;
    private final String[] params;

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

    public static CmdTypes nameToType(String name) {
        for (CmdTypes t : CmdTypes.values()) {
            if (name.equals(t.name)) return t;
        }
        return null;
    }

    public String[] getParams() {
        String[] ret = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            ret[i] = params[i];
        }
        return ret;
    }

    public int getParamsCount() {
        return params.length;
    }
}
