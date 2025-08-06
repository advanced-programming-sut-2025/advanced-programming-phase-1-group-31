package common;

import java.util.ArrayList;

public class Lobby {
    private String lobbyName;
    private String lobbyPassword;
    private int lobbyID;
    private final boolean isPrivate;
    private boolean isStarted;
    private boolean isVisible;
    private long creationTime;
    private final ArrayList<String> players = new ArrayList<>();
    private String adminUsername;

    public Lobby(String lobbyName, String lobbyPassword, int lobbyID, Player admin, boolean isVisible) {
        // TODO: check if id is used or not
        this.lobbyName = lobbyName;
        this.lobbyPassword = lobbyPassword;
        this.lobbyID = lobbyID;
        this.isPrivate = true;
        this.adminUsername = admin.getUsername();
        players.add(admin.getUsername());
        isStarted = false;
        this.isVisible = isVisible;
        this.creationTime = System.currentTimeMillis();
    }

    public Lobby(String lobbyName, int lobbyID, Player admin, boolean isVisible) {
        this.lobbyName = lobbyName;
        this.lobbyID = lobbyID;
        this.isPrivate = false;
        this.adminUsername = admin.getUsername();
        players.add(admin.getUsername());
        isStarted = false;
        this.isVisible = isVisible;
        this.creationTime = System.currentTimeMillis();
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getLobbyPassword() {
        return lobbyPassword;
    }

    public void setLobbyPassword(String lobbyPassword) {
        this.lobbyPassword = lobbyPassword;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public void setLobbyID(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getAdmin() {
        return adminUsername;
    }

    public void setAdmin(String admin) {
        this.adminUsername = admin;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public void addPlayer(Player player) throws IllegalArgumentException {
        if (players.contains(player.getUsername())) {
            throw new IllegalArgumentException("Player already exists!");
        } else if (players.size() == 4) {
            throw new IllegalArgumentException("Lobby cannot have more\n than 4 players!");
        } else if (isStarted) {
            throw new IllegalArgumentException("Lobby is already started!");
        }
        players.add(player.getUsername());
    }

    public void removePlayer(String username) {
        players.remove(username);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public boolean isPlayerInThisLobby(Player player) {
        return players.contains(player.getUsername());
    }

    public ArrayList<String> getPlayers() {
        return players;
    }
}
