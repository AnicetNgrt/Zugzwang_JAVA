package Communication;

public enum CmdTypes {
    // NON-GAME COMMANDS
    CONNECTIONCONFIRM("connectconfirm", 0),
    MYNAMEIS("myname", 1, "name"),
    GIVEID("giveid", 0),
    RECEIVEID("receiveid", 1, "id"),
    HOST("host", 2, "gameName", "password", "maxPlayerCount", "spectatorsAllowed"),
    JOIN("join", 1, "gameId"),
    REQUESTLOBBYPASSWORD("requestlobbypassword", 0),
    GIVELOBBYPASSWORD("givelobbypassword", 1, "password"),
    REQUESTLOBBIES("requestlobbies", 0, "count", "offset"),
    RECEIVELOBBYDATA("receivelobbydata", 2, "gameId", "gameName", "hasPassword", "partOfList", "maxPlayerCount", "spectatorsAllowed", "playerCount", "spectatorCount"),
    ERROR("error", 1, "reason"),
    DISCONNECT("disconnect", 0),
    RETRY("retry", 1, "reason"),
    PING("ping", 1, "message", "integer"),

    // LOBBY COMMANDS
    GIVEMYLOBBYDATA("givemylobbydata", 0),
    SENDALLPLAYERS("sendallplayers", 0),
    STARTGAME("startgame", 0),
    KICKFROMLOBBY("kickfromlobby", 1, "nameInGame"),
    ADDPLAYER("addplayer", 1, "nameInGame", "isSpectator"),
    REMOVEPLAYER("removeplayer", 1, "nameInGame", "isSpectator");

    private final String name;
    private final int strCount;
    private final String[] params;

    public static CmdTypes[] clientCmds = {MYNAMEIS, GIVEID, HOST, JOIN, REQUESTLOBBIES, DISCONNECT, PING, GIVELOBBYPASSWORD};
    public static CmdTypes[] lobbyCmds = {GIVEMYLOBBYDATA, STARTGAME, KICKFROMLOBBY};
    public static CmdTypes[] inGameCmds = {};

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
        System.arraycopy(params, 0, ret, 0, params.length);
        return ret;
    }

    public int getParamsCount() {
        return params.length;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder(name + " ");
        int i = 0;
        for (String p : params) {
            ret.append(" <").append(p).append(">");
            ret.append(i++ < strCount ? "(Str)" : "(int)");
        }
        return ret.toString();
    }
}
