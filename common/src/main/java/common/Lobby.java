package common;

import java.util.ArrayList;
import java.util.Random;

public class Lobby {
    private String lobbyName;
    private String lobbyPassword;
    private int lobbyID;
    private final boolean isPrivate;
    private final ArrayList<Player> players =  new ArrayList<>();

    public Lobby(String lobbyName, String lobbyPassword, int lobbyID) {
        this.lobbyName = lobbyName;
        this.lobbyPassword = lobbyPassword;
        this.lobbyID = lobbyID;
        // TODO: check if id is used or not
        this.isPrivate = true;
    }

    public Lobby(String lobbyName, int lobbyID) {
        this.lobbyName = lobbyName;
        this.lobbyID = lobbyID;
        this.isPrivate = false;
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

    public void addPlayer(Player player) throws IllegalArgumentException {
        if (players.contains(player)) {
            throw new IllegalArgumentException("Player already exists!");
        }

        else if (players.size() == 4) {
            throw new IllegalArgumentException("Lobby cannot have more\n than 4 players!");
        }
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }
}
