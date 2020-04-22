package NetworkingClasses;

public enum CmdTypes {
    // NON-GAME COMMANDS
    MYNAMEIS("myname", false, 1, "name"),
    GIVEID("giveid", false, 1, "id"),
    HOST("host", false, 2, "gameName", "password", "maxPlayerCount", "spectatorsAllowed"),
    JOIN("join", false, 1, "gameId"),
    ERROR("error", false, 1, "reason"),
    DISCONNECT("disconnect", false, 0),
    RETRY("retry", false, 1, "reason"),

    // GAME COMMANDS
    KICKFROMGAME("kickfromgame", true, 0),
    ADDPLAYER("addplayer", true, 2, "playerName", "playerId"),
    REMOVEPLAYER("removeplayer", true, 2, "playerName", "playerId");

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
