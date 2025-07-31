package common;

import java.util.Random;

public class Lobby {
    private final Random random = new Random();
    private String lobbyName;
    private String lobbyPassword;
    private int lobbyID;
    private final boolean isPrivate;

    public Lobby(String lobbyName, String lobbyPassword) {
        this.lobbyName = lobbyName;
        this.lobbyPassword = lobbyPassword;
        this.lobbyID = random.nextInt(10000) + 1;
        // TODO: check if id is used or not
        this.isPrivate = true;
    }

    public Lobby(String lobbyName) {
        this.lobbyName = lobbyName;
        this.lobbyID = random.nextInt(10000) + 1;
        // TODO: check if id is used or not
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
}
