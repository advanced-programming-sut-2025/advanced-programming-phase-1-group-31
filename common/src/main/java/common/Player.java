package common;

public class Player {
    private String username;
    private String password;
    private String nickName;
    private Lobby lobby;

    public Player(String username, String password, String nickName, Lobby lobby) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.lobby = lobby;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }
}
