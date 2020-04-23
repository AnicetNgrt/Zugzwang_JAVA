package Communication;

public enum CmdTypes {
    // NON-GAME COMMANDS
    CONNECTIONCONFIRM("connectconfirm", false, 0),
    MYNAMEIS("myname", false, 1, "name"),
    GIVEID("giveid", false, 1, "id"),
    HOST("host", false, 2, "gameName", "password", "maxPlayerCount", "spectatorsAllowed"),
    JOIN("join", false, 1, "gameId"),
    RECEIVELOBBYDATA("receivelobbydata", false, 1, "gameName", "maxPlayerCount", "spectatorsAllowed", "playerCount", "spectatorCount"),
    ERROR("error", false, 1, "reason"),
    DISCONNECT("disconnect", false, 0),
    RETRY("retry", false, 1, "reason"),
    PING("ping", false, 1, "message", "integer"),

    // GAME COMMANDS
    KICKFROMGAME("kickfromgame", true, 1, "nameInGame"),
    ADDPLAYER("addplayer", true, 1, "nameInGame"),
    REMOVEPLAYER("removeplayer", true, 1, "nameInGame");

    private String name;
    private int strCount;
    private String[] params;
    private boolean isGameCommand;

    CmdTypes(String n, boolean isGameCommand, int sc, String... p) {
        name = n;
        strCount = sc;
        params = p;
        this.isGameCommand = isGameCommand;
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

    public boolean isGameCommand() {
        return isGameCommand;
    }

}
